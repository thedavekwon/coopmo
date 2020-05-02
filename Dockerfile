FROM adoptopenjdk/openjdk11:latest
FROM maven:3-jdk-11

WORKDIR /app
COPY pom.xml ./
RUN mvn verify clean --fail-never

COPY . ./
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/target/coopmo-0.0.1-SNAPSHOT.jar"]