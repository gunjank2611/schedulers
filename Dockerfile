# Dockerfile
FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TcpSchedulersApplication-0.0.1-SNAPSHOT.jar
EXPOSE 7072
ENTRYPOINT ["java","-jar","/TcpSchedulersApplication-0.0.1-SNAPSHOT.jar"]