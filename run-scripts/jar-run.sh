#!/bin/bash
#
# Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
# Silesian University of Technology
#

start_java_heap_size="64m" # -Xms parameter
max_java_heap_size="128m"  # -Xmx parameter

if [ -n "$1" ]; then
  cd "$1" || exit 0
fi

exec_jar_file_name="air-hub-master-server-[0-9]\.[0-9]\.[0-9]\.jar"
exec_jar_file_name=$(find . -name "$exec_jar_file_name" -exec echo {} \;)

exec_pid_jar_file_name=${exec_jar_file_name#./}
PID=$(pgrep -f "$exec_pid_jar_file_name")
if [ "$PID" != "" ]; then
  kill "$PID"
  echo "[bash run script info] <> Process on proxy domain with PID '$PID' was terminated"
fi

if [ "$exec_jar_file_name" == "" ]; then
  echo "[bash run script err] <> Executable JAR file not found in current directory"
  exit 1
fi

if [ ! -f ".env" ]; then
  echo "[bash run script err] <> Env file not found in current directory"
  exit 6
fi

exec_script="nohup java
-XX:+UseSerialGC
-Xss512k
-XX:MaxRAM=$max_java_heap_size
-Xms$start_java_heap_size
-Xmx$max_java_heap_size
-Dspring.profiles.active=prod
-XX:NativeMemoryTracking=summary
-jar $exec_jar_file_name
"

exec_script=$(echo "$exec_script" | tr '\n' ' ')

echo "[bash run script info] <> Executing AirHubMaster on proxy domain in production silent mode"
echo "[bash run script info] <> $exec_script > /dev/null 2>&1 &"

export JAVA_VERSION="17"
$exec_script >/dev/null 2>&1 &
