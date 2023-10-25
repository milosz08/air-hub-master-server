#!/bin/bash
#
# Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
# Silesian University of Technology
#

java \
  -XX:+UseSerialGC \
  -Xss512k \
  -XX:MaxRAM="$XMX" \
  -Xms"$XMS" \
  -Xmx"$XMX" \
  -Dspring.profiles.active="$SPRING_PROFILES_ACTIVE" \
  -XX:NativeMemoryTracking=summary \
  -jar \
  ahmapi-embeddable.jar
