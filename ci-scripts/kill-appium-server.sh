#!/bin/zsh
kill $(ps | grep 'appium' | grep -vE -- 'grep|\.sh' | awk '{print $1}')

if [ -z $(ps | grep 'appium' | grep -vE -- 'grep|\.sh') ] 
then
    echo "No running Appium Server processes detected."
    echo "All previously running Appium Server processes have been successfully killed."
    exit 0
else
    echo "Failed to kill Appium Server process"
    exit -4
fi