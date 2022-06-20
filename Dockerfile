FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=./BE/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILES}","-jar","/app.jar"]
