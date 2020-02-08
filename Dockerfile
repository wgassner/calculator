FROM adoptopenjdk/openjdk8:alpine-slim
COPY /target/calculator-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]