#!/bin/bash

#
# Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
#
# File name: docker-assembly.sh
# Last modified: 6/12/23, 9:22 PM
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

XMS_ARG_KEY="-Dxms"
XMX_ARG_KEY="-Dxmx"

START_JAVA_HEAP_SIZE="128m"
MAX_JAVA_HEAP_SIZE="256m"

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
