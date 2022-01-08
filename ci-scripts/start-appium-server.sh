set -x

IP='127.0.0.1'
PORT='4723'

APPIUM_PATH=$(which appium | grep '/')
if [ $(which appium) ]
then
      echo "Found command-line Appium at $APPIUM_PATH"
      echo "Launching on $IP:$PORT"
else
      echo "Couldn't find command-line version of Appium Server."
      exit -1
fi

$(appium -a $IP -p $PORT) &

if [ 'nc -vz $IP $PORT' ]
then
      echo "Connection at $IP:$PORT is open."
      echo "You may now run your tests."
else
      echo "Appium server started, but $IP:$PORT is not available."
      exit -3
fi

exit 0
