#!/bin/bash

#
# Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
#
# File name: jar-run.sh
# Last modified: 19/05/2023, 22:05
# Project name: air-hub-master-server
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
# file except in compliance with the License. You may obtain a copy of the License at
#
#     <http://www.apache.org/license/LICENSE-2.0>
#
# Unless required by applicable law or agreed to in writing, software distributed under
# the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
# OF ANY KIND, either express or implied. See the License for the specific language
# governing permissions and limitations under the license.
#

START_JAVA_HEAP_SIZE="64m"     # -Xms parameter
MAX_JAVA_HEAP_SIZE="128m"      # -Xmx parameter

if [ -n "$1" ]; then
    cd "$1" || exit 0
fi

EXEC_JAR_FILE_NAME="air-hub-master-server-[0-9]\.[0-9]\.[0-9]\.jar"
EXEC_JAR_FILE_NAME=$(find . -name "$EXEC_JAR_FILE_NAME" -exec  echo {} \;)

EXEC_PID_JAR_FILE_NAME=${EXEC_JAR_FILE_NAME#./}
PID=$(pgrep -f "$EXEC_PID_JAR_FILE_NAME")
if [ "$PID" != "" ]; then
    kill "$PID"
    echo "[bash run script info] <> Process on proxy domain with PID '$PID' was terminated"
fi

if [ "$EXEC_JAR_FILE_NAME" == "" ]; then
    echo "[bash run script err] <> Executable JAR file not found in current directory"
    exit 1
fi

if [ ! -f ".env" ]; then
    echo "[bash run script err] <> Env file not found in current directory"
    exit 6
fi

EXEC_SCRIPT="nohup java
-XX:+UseSerialGC
-Xss512k
-XX:MaxRAM=$MAX_JAVA_HEAP_SIZE
-Xms$START_JAVA_HEAP_SIZE
-Xmx$MAX_JAVA_HEAP_SIZE
-Dspring.profiles.active=prod
-XX:NativeMemoryTracking=summary
-jar $EXEC_JAR_FILE_NAME
"

EXEC_SCRIPT=$(echo "$EXEC_SCRIPT" | tr '\n' ' ')

echo "[bash run script info] <> Executing AirHubMaster on proxy domain in production silent mode"
echo "[bash run script info] <> $EXEC_SCRIPT > /dev/null 2>&1 &"

export JAVA_VERSION="17"
$EXEC_SCRIPT > /dev/null 2>&1 &
