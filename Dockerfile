FROM gradle:4.7.0-jdk13-alpine AS build
COPY --chown=gradle:gradle . /
RUN ./gradlew build --no-daemon 

FROM openjdk:13-jdk-alpine
ADD build/libs/todo-*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
