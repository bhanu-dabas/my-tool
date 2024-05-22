def call() {
    // Clone the repository
    git branch: 'main', url: 'https://github.com/bhanu-dabas/my-tool.git'
    // User Approval
    input message: 'Approve the deployment?', submitter: 'admin,bhanu'
    // Playbook Execution
    ansiblePlaybook(
        inventory: 'hosts',
        playbook: 'pb.yml'
    )
}
