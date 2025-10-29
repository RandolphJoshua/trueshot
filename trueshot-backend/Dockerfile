FROM openjdk:17-oracle
LABEL maintainer="trueshot@ongtangco.com"
WORKDIR /opt/app
EXPOSE 8080
COPY target/trueshot-1.0.0-SNAPSHOT.jar /opt/app/trueshot.jar
ENTRYPOINT ["java", "-jar", "trueshot.jar"]
