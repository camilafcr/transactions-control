FROM openjdk:8-jdk-alpine
ADD target/transactions-control-0.0.1-SNAPSHOT.jar application.jar
WORKDIR /application
COPY target/transactions-control-0.0.1-SNAPSHOT.jar /application/application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]
EXPOSE 8080d