FROM openjdk:13-jdk-alpine as build

ADD --chown=gradle:gradle gradle gradle
ADD --chown=gradle:gradle src src
ADD --chown=gradle:gradle gradlew .
ADD --chown=gradle:gradle settings.gradle .
ADD --chown=gradle:gradle build.gradle .
RUN ./gradlew build --no-daemon

FROM openjdk:13-jdk-alpine

COPY --from=build --chown=app:app build/libs/todo-*.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 7000
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
