#!/usr/bin/env python3
import boto3
import json

def get_aws_inventory():
    ec2 = boto3.client('ec2', region_name='ap-south-1')
    response = ec2.describe_instances()

    inventory = {
        'all': {'hosts': []},
        '_meta': {'hostvars': {}}
    }

    private_key_path = '/home/dabas/bhanu.pem'  # Specify the path to your PEM file here

    for reservation in response['Reservations']:
        for instance in reservation['Instances']:
            if instance['State']['Name'] == 'running':
                os = ''
                public_ip = instance.get('PublicIpAddress')
                if public_ip:
                    inventory['all']['hosts'].append(public_ip)
                    inventory['_meta']['hostvars'][public_ip] = {
                        'ansible_host': public_ip,
                        'ansible_ssh_private_key_file': private_key_path,  # Specify the private key path
                        'ansible_user': 'ubuntu'  # Adjust the user name as needed
                    }
                for tag in instance['Tags']:
                    if tag['Key'] == 'OS':
                        os = tag['Value'].lower()
                if os:
                    inventory[os]['hosts'].append(public_ip)

    return inventory

if __name__ == '__main__':
    print(json.dumps(get_aws_inventory()))
