#
# Build stage
#
FROM gradle:6.5.1-jdk14 as compiler
COPY . .
RUN ./gradlew build

#
# Run stage
#
FROM adoptopenjdk:14.0.1_7-jre-openj9-0.20.0

ENV SERVER_PORT="8080"
ENV NEW_RELIC_LICENSE_KEY=""
ENV NEW_RELIC_APP_KEY=""
ENV NEW_RELIC_ACCOUNT_ID=""
ENV NEW_RELIC_DISTRIBUTED_TRACING=true

ENV SECURITY_OPTS="-Dnetworkaddress.cache.negative.ttl=0 -Dnetworkaddress.cache.ttl=0"
ENV SERVER_IP=""
ENV CLIENT_HEADERS=""
ENV JAVA_TOOL_OPTIONS="-Dhttps.protocols=TLSv1.2"
ENV MAX_RAM_PERCENTAGE="-XX:MaxRAMPercentage=70"
ENV MIN_RAM_PERCENTAGE="-XX:MinRAMPercentage=70"
ENV JIGSAW_ARG="--add-opens=java.base/java.net=ALL-UNNAMED"
ENV OPENDJ_ARGS="-XX:TieredStopAtLevel=1 -noverify -XX:+AlwaysPreTouch -Xshareclasses -Xquickstart"
ENV NEW_RELIC_ARG="-javaagent:newrelic/newrelic.jar -Dnewrelic.config.distributed_tracing.enabled=$NEW_RELIC_DISTRIBUTED_TRACING"
ENV JAVA_OPTS="$JAVA_OPTS $JIGSAW_ARG $NEW_RELIC_ARG $SECURITY_OPTS $MAX_RAM_PERCENTAGE $MIN_RAM_PERCENTAGE $JAVA_TOOL_OPTIONS $OPENDJ_ARGS"
ENV APP_HOME /app

EXPOSE $SERVER_PORT

COPY --from=compiler /src/main/resources/newrelic/newrelic.yml $APP_HOME/newrelic/
COPY --from=compiler /build/libs/newrelic.jar app/newrelic/newrelic.jar
COPY --from=compiler /build/libs/dynamo-account-acid.jar $APP_HOME/dynamo-account-acid.jar

WORKDIR $APP_HOME

ENTRYPOINT exec java $JAVA_OPTS -jar api.jar
CMD ["exec java ${JAVA_OPTS} -Dnewrelic.environment=$ENV -javaagent:./newrelic/newrelic.jar -jar "]
