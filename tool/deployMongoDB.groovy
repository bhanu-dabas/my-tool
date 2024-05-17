def call() {
    // Clone the repository
    git branch: 'main', url: 'https://github.com/bhanu-dabas/bhanusingh.git'
    // User Approval
    input message: 'Approve the deployment?', submitter: 'admin,bhanu-dabas'
    // Playbook Execution
    ansiblePlaybook(
        inventory: 'hosts',
        playbook: 'mongodb_install.yml'
    )
    // Notification
    emailext(
        subject: 'Ansible Deployment Status',
        body: 'The Ansible deployment has completed.',
        to: 'bhanudabas7@gmail.com'
    )
}
