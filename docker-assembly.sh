#!/bin/bash
#
# Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
# Silesian University of Technology
#

XMS_ARG_KEY="-Dxms"
XMX_ARG_KEY="-Dxmx"

for arg in "$@"; do
  if [[ $arg == -D* ]]; then
    VALUE="${arg#*=}"
    if [[ $arg == "$XMS_ARG_KEY"* ]]; then
      START_JAVA_HEAP_SIZE="$VALUE"
    fi
    if [[ $arg == "$XMX_ARG_KEY"* ]]; then
      MAX_JAVA_HEAP_SIZE="$VALUE"
    fi
  fi
done

BUILD_DATE=$(date +%Y%m%d%H%M%S)
export BUILD_DATE

export START_JAVA_HEAP_SIZE
export MAX_JAVA_HEAP_SIZE

echo "[bash docker script info] <> Preparing bootable JAR directory..."
./gradlew --parallel --max-workers=4 clean --warning-mode none

echo "[bash docker script info] <> Creating bootable JAR directory..."
./gradlew --parallel --max-workers=8 bootJar --warning-mode none

echo "[bash docker script info] <> Clearing..."
docker image prune -a -f

echo "[bash docker script info] <> Running Docker cluster in development mode..."
docker-compose -f "docker-compose.yml" up
