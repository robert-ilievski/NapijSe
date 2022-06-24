FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
COPY /target/napijse-0.0.1-SNAPSHOT.jar napijse.jar
CMD ["java", "-jar", "napijse.jar"]