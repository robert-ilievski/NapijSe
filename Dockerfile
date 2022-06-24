FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
ADD target/napijse-0.0.1-SNAPSHOT.jar napijse.jar
ENTRYPOINT ["java", "-jar", "napijse.jar"]