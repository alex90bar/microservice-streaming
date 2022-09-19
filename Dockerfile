FROM adoptopenjdk:11-jre-hotspot
WORKDIR src
ADD impl/target/microservice-streaming-impl-1.0.0-SNAPSHOT.jar microservice-streaming.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "microservice-streaming.jar"]