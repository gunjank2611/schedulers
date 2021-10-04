# Dockerfile
FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TcpSalesforceMngmtApplication-0.0.1-SNAPSHOT.jar
EXPOSE 7071
ENTRYPOINT ["java","-jar","/TcpSalesforceMngmtApplication-0.0.1-SNAPSHOT.jar"]