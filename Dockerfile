FROM openjdk:13-jdk-alpine
VOLUME /tmp
ADD build/libs/todo-*.jar app.jar
COPY todo.db todo.db
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
