#!/usr/bin/env bash

# This script install Jenkins in your Ubuntu

# This script must be run as root:
#   $ sudo ./jenkins_install.sh

# Install dependency 
sudo apt-get install openjdk-11-jdk -y 

# install jenkins stable version  steps
# download deb packages
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null

# add binary deb package
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

# update packages
sudo apt-get update

# install jenkins
sudo apt-get install -y jenkins 

# start jenkins 
sudo systemctl start jenkins.service

# status jenkins
sudo systemctl status jenkins.service


## 
echo "jenkins install completed"

echo "connect https://ipaddress:8080"

# get password 
sudo cat /var/lib/jenkins/secrets/initialAdminPassword 
