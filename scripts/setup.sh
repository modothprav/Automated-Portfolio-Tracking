#!/bin/bash

# Script Obtained from
# https://github.com/SeleniumHQ/selenium/blob/selenium-4.0.0-beta-3/.github/actions/setup-chrome/action.yml

wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
echo "deb http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee -a /etc/apt/sources.list.d/google-chrome.list
sudo apt-get update -qqy
sudo apt-get -qqy install google-chrome-stable
CHROME_VERSION=$(google-chrome-stable --version)
CHROME_FULL_VERSION=${CHROME_VERSION%%.*}
CHROME_MAJOR_VERSION=${CHROME_FULL_VERSION//[!0-9]}
sudo rm /etc/apt/sources.list.d/google-chrome.list
export CHROMEDRIVER_VERSION=`curl -s https://chromedriver.storage.googleapis.com/LATEST_RELEASE_${CHROME_MAJOR_VERSION%%.*}`
curl -L -O "https://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip"
unzip chromedriver_linux64.zip && chmod +x chromedriver
export CHROMEDRIVER_VERSION=`curl -s https://chromedriver.storage.googleapis.com/LATEST_RELEASE_${CHROME_MAJOR_VERSION%%.*}`
curl -L -O "https://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip"
unzip chromedriver_linux64.zip && chmod +x chromedriver
chromedriver -version