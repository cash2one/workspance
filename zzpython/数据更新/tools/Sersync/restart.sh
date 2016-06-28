#!/bin/bash
/usr/tools/Sersync/stop.sh > /dev/null 2>&1
echo "stop succ"
/usr/tools/Sersync/start.sh > /dev/null 2>&1
echo "start succ"