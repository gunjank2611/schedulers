# Dockerfile
FROM openjdk:11-jdk-slim
RUN apt-get -y update
RUN apt-get install -y apt-transport-https ca-certificates lsb-release gnupg
RUN curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
RUN touch /etc/apt/sources.list.d/kubernetes.list 
RUN echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" | tee -a /etc/apt/sources.list.d/kubernetes.list
RUN apt-get -y update
RUN apt-get install -y kubectl
RUN curl -sL https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor | tee /etc/apt/trusted.gpg.d/microsoft.gpg > /dev/null
RUN echo "deb [arch=amd64] https://packages.microsoft.com/repos/azure-cli/ bullseye main" | tee /etc/apt/sources.list.d/azure-cli.list
RUN apt-get -y update
RUN apt-get -y install azure-cli
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TcpSchedulersApplication-0.0.1-SNAPSHOT.jar
EXPOSE 7072
ENTRYPOINT ["java","-jar","/TcpSchedulersApplication-0.0.1-SNAPSHOT.jar"]