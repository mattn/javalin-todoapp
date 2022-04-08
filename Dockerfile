FROM openjdk:13-jdk-alpine
VOLUME /tmp
ADD build/libs/todo-*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
