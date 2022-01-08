# -no-snapshot-save will help us in killing emulator after tests.
emulator @andro80 -no-snapshot-save &
while [ -z "$(adb shell getprop dev.bootcomplete | grep '1')" ]
    do
        echo "Waiting for emulator..."
        sleep 1
    done && echo "Emulator booted."