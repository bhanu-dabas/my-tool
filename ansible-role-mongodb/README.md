# Ansible role for MongoDB
![Ubuntu](https://github.com/OlaSegha/ansible-role-mongodb/actions/workflows/ubuntu.yml/badge.svg)

Ansible role to install and manage [MongoDB](http://www.mongodb.org/).

- Install and configure the MongoDB
- Configure mongodb users
- Configure authentication

Originally a fork from [Undergreen](https://github.com/UnderGreen/ansible-role-mongodb)

#### Variables

```yaml
# This variable is used to set source of MongoDB installation.
# 'mongodb' - version provided by Debian-based distributions from their official package repositories.
# 'mongodb-org' - version provided by MongoDB package repository.
# 'mongodb' is not included in th role test matrix and working of it is not guarantied.
mongodb_package: mongodb-org

# `mongodb_version` variable sets version of MongoDB.
# Should be '5.0', '6.0' or '7.0'.

mongodb_pymongo_from_pip: true # Install latest PyMongo via PIP or package manager
mongodb_pymongo_pip_version: 3.6.1 # Choose PyMong version to install from pip. If not set use latest
mongodb_user_update_password: "on_create" # MongoDB user password update default policy
mongodb_manage_service: true
mongodb_manage_systemd_unit: true

# Disable transparent hugepages on systemd debian based installations
mongodb_disable_transparent_hugepages: false

# You can enable or disable NUMA support
mongodb_use_numa: true

mongodb_user: "{{ 'mongod' if ('RedHat' == ansible_os_family) else 'mongodb' }}"
mongodb_uid:
mongodb_gid:
mongodb_daemon_name: "{{ 'mongod' if ('mongodb-org' in mongodb_package) else 'mongodb' }}"
## net Options
mongodb_net_bindip: 127.0.0.1 # Comma separated list of ip addresses to listen on
mongodb_net_http_enabled: false # Enable http interface
mongodb_net_ipv6: false # Enable IPv6 support (disabled by default)
mongodb_net_maxconns: 65536 # Max number of simultaneous connections
mongodb_net_port: 27017 # Specify port number

## processManagement Options
mongodb_processmanagement_fork: false # Fork server process

## security Options
# Disable or enable security. Possible values: 'disabled', 'enabled'
mongodb_security_authorization: "disabled"
mongodb_security_keyfile: /etc/mongodb-keyfile # Specify path to keyfile with password for inter-process authentication

## storage Options
mongodb_storage_dbpath: /data/db # Directory for datafiles
mongodb_storage_dirperdb: false # Use one directory per DB

# The storage engine for the mongod database
mongodb_storage_engine: "wiredTiger"
# mmapv1 specific options
mongodb_storage_quota_enforced: false # Limits each database to a certain number of files
mongodb_storage_quota_maxfiles: 8 # Number of quota files per DB
mongodb_storage_smallfiles: false # Very useful for non-data nodes

mongodb_storage_journal_enabled: true # Enable journaling
mongodb_storage_prealloc: true # Disable data file preallocation

# WiredTiger Options
mongodb_wiredtiger_cache_size: 1 # Cache size for wiredTiger in GB

## systemLog Options
## The destination to which MongoDB sends all log output. Specify either 'file' or 'syslog'.
## If you specify 'file', you must also specify mongodb_systemlog_path.
mongodb_systemlog_destination: "file"
mongodb_systemlog_logappend: true # Append to logpath instead of over-writing
mongodb_systemlog_path: /var/log/mongodb/{{ mongodb_daemon_name }}.log # Log file to send write to instead of stdout


## setParameter options
# Configure setParameter option.
# Example :
mongodb_set_parameters:
  {
    "enableLocalhostAuthBypass": "true",
    "authenticationMechanisms": "SCRAM-SHA-1,MONGODB-CR",
  }

# Log rotation
mongodb_logrotate: true # Rotate mongodb logs.
mongodb_logrotate_options:
  - compress
  - copytruncate
  - daily
  - dateext
  - rotate 7
  - size 10M

# names and passwords for administrative users
mongodb_user_admin_name: siteUserAdmin
mongodb_user_admin_password: passw0rd

mongodb_root_admin_name: siteRootAdmin
mongodb_root_admin_password: passw0rd

mongodb_root_backup_name: backupuser
mongodb_root_backup_password: passw0rd
```

#### Usage

Add `olasegha.mongodb` to your roles and set vars in your playbook file.

Example vars for authorization:

```yaml
mongodb_security_authorization: "enabled"
mongodb_users:
  - {
    name: testUser,
    password: passw0rd,
    roles: readWrite,
    database: app_development
}
```

Example vars for oplog user:

```yaml
mongodb_oplog_users:
  - {
    user: oplog,
    password: passw0rd
}
```

Required vars to change on production:

```yaml
mongodb_user_admin_password
mongodb_root_admin_password
mongodb_root_backup_password

Licensed under the GPLv2 License. See the [LICENSE.md](LICENSE.md) file for details.

#### Feedback, bug-reports, requests, ...

Are [welcome](https://github.com/OlaSegha/ansible-role-mongodb/issues)!
