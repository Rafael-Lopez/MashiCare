FROM openjdk:8-jdk-alpine
COPY target/mashicare-0.0.1-SNAPSHOT.jar mashicare-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/mashicare-0.0.1-SNAPSHOT.jar"]
