FROM adoptopenjdk:11-jre-hotspot
WORKDIR /usr/src/app
EXPOSE 8080
COPY napijse-0.0.1-SNAPSHOT.jar napijse.jar
CMD ["java", "-jar", "napijse.jar"]