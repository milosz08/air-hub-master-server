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
