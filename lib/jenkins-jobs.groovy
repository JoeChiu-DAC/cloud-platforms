node {

    project = "cloud-platforms"
    script = "stop-vms.yml"
    def task = "gcp"
    def workspace = pwd()
    def repositoryUrl = "https://github.com/joehmchiu/${project}.git"
    def branch = "master"
    def msg = "VMs powered off"

    withEnv(["PATH+ANSIBLE=${tool 'ansible-2.4.2.0'}"]) {

        stage 'Confirm'
        timeout(time: 30, unit: 'SECONDS') {
            input "${msg}?"
        }
        
        stage 'Init Working Env'
        sh "sudo /opt/bin/init.sh ${project} ${task}"

        stage 'Check List'
        echo "\u2600 workspace=${workspace}/"
        sh "ls -ltrh ${workspace}"

        stage 'Check Ansible Availability'
        sh 'which ansible'

        stage 'Check Ansible Version'
        sh 'ansible --version'

        stage 'SCM Update'
        git url: repositoryUrl, branch: branch
        sh "sudo cp -rf ./${task}/* /opt/${project}/${task}/."

        stage 'Run Playbook Tasks'
        ansiblePlaybook (
            playbook: "/opt/${project}/${task}/${script}",
            sudo: true
        )

        stage "Tasks Logged"
        sh "sudo /opt/bin/log.sh '${msg}'"
        
        stage 'Finalize Task'
        sh "sudo /opt/bin/fin.sh ${project} ${task}"        
    }
}