def call() {
    // Clone the repository
    git branch: 'main', url: 'https://github.com/bhanu-dabas/my-tool.git'
    // User Approval
    input message: 'Approve the deployment?', submitter: 'admin,bhanu-dabas'
    // Playbook Execution
    ansiblePlaybook(
        inventory: 'dynamic_inventory_script.py',
        playbook: 'pb.yml'
    )
}
