#!/bin/bash
#
# Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
# Silesian University of Technology
#

if [ -n "$1" ]; then
  cd "$1" || exit 0
fi

exec_jar_file_name="air-hub-master-server-[0-9]\.[0-9]\.[0-9]\.jar"
exec_jar_file_name=$(find . -name "$exec_jar_file_name" -exec echo {} \;)

if [ "$exec_jar_file_name" == "" ]; then
  echo "[bash kill script err] <> Executable JAR file not found in current directory"
  exit 1
fi

exec_jar_file_name=${exec_jar_file_name#./}
pid=$(pgrep -f "$exec_jar_file_name")
if [ "$pid" == "" ]; then
  echo "[bash kill script info] <> Process on proxy domain is not running"
  exit 0
fi

kill "$pid"
echo "[bash kill script info] <> Process on proxy domain with pid '$pid' was terminated"
