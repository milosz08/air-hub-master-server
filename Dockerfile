FROM eclipse-temurin:17-jdk-alpine AS build

ENV BUILD_DIR=/build/air-hub-master-server

RUN mkdir -p $BUILD_DIR
WORKDIR $BUILD_DIR

# copy only gradle-based resources for optimized caching
COPY gradle $BUILD_DIR/gradle
COPY gradlew $BUILD_DIR/gradlew
COPY build.gradle $BUILD_DIR/build.gradle
COPY settings.gradle $BUILD_DIR/settings.gradle

RUN chmod +x $BUILD_DIR/gradlew
RUN cd $BUILD_DIR

RUN ./gradlew dependencies --no-daemon

# copy rest of resources
COPY /docker $BUILD_DIR/docker
COPY /src $BUILD_DIR/src

RUN ./gradlew clean --no-daemon
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine

ENV BUILD_DIR=/build/air-hub-master-server
ENV ENTRY_DIR=/app/air-hub-master-server
ENV JAR_NAME=air-hub-master-server.jar

WORKDIR $ENTRY_DIR

COPY --from=build $BUILD_DIR/.bin/$JAR_NAME $ENTRY_DIR/$JAR_NAME
COPY --from=build $BUILD_DIR/docker/entrypoint $ENTRY_DIR/entrypoint

RUN sed -i \
  -e "s/\$JAR_NAME/$JAR_NAME/g" \
  entrypoint

RUN chmod +x entrypoint

LABEL maintainer="Miłosz Gilga <miloszgilga@gmail.pl>"

EXPOSE 8080
ENTRYPOINT [ "./entrypoint" ]
