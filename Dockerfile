#
# Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
#
# File name: Dockerfile
# Last modified: 19/05/2023, 20:23
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

FROM amazoncorretto:17

ENV BUILD_DIR /build/jar
ENV ENTRY_DIR /jwizard-container

RUN mkdir $ENTRY_DIR

COPY $BUILD_DIR/entrypoint.sh $ENTRY_DIR
COPY $BUILD_DIR/*.jar $ENTRY_DIR

WORKDIR $ENTRY_DIR

RUN mv *.jar ahmapi-embeddable.jar
RUN chmod +x entrypoint.sh

EXPOSE 8080
ENTRYPOINT [ "./entrypoint.sh" ]
