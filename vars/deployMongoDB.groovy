def call() {
    // Clone the repository
    git branch: 'main', url: 'https://github.com/bhanu-dabas/my-tool.git'
    
    // Automatically approve the deployment
    def approved = true
    
    // Playbook Execution if approved
    if (approved) {
        ansiblePlaybook(
            inventory: 'hosts',
            playbook: 'pb.yml'
        )
    } else {
        echo "Deployment not approved. Exiting."
    }
}
