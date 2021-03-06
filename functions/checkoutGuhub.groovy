#!/usr/bin/env groovy

def githubCheckout(checkoutFolder, githubPathProvided, credential_repo, github_branch) {

	dir(checkoutFolder) {
		if(!githubPathProvided.contains("https://")) {
			githubPathProvided = "https://github.com/" + githubPathProvided
		}
	
		checkout([
			$class: 'GitSCM', 
			branches: [[
				name: "*/${github_branch}"
			]], 
			doGenerateSubmoduleConfigurations: false, 
			extensions: [[
				$class: 'SubmoduleOption', 
				disableSubmodules: false, 
				parentCredentials: true, 
				recursiveSubmodules: false, 
				reference: '', 
				trackingSubmodules: false
			]], 
			submoduleCfg: [], 
			userRemoteConfigs: [[
				credentialsId: credential_repo, 
				url: "${githubPathProvided}"
			]]
		])
	
		string githubRepoName = githubPathProvided.split('/')[-1]
		if (githubRepoName =~ '.git') {
			githubRepoName = githubRepoName.replace('.git', '')
		}
		string githubOrgName = githubPathProvided.split('/')[-2]
		git_commit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
	}
	echo "git_commit: " + git_commit
	echo "githubRepoName: " + githubRepoName
	echo "githubOrgName: "  + githubOrgName
	return [githubRepoName, githubOrgName, git_commit]
}

return this