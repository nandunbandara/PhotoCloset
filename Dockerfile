FROM ubuntu:16.04

RUN apt-get update
RUN apt-get install default-jre -y
ADD ./target/photocloset-0.0.1-SNAPSHOT.jar ./app
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app/photocloset-0.0.1-SNAPSHOT.jar"]
