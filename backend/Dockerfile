# Build stage
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /project

COPY pom.xml .

COPY src/ /project/src

RUN mvn clean install -DskipTests=false

# Run stage
FROM eclipse-temurin:17-jre-alpine

RUN mkdir /app

COPY --from=build /project/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t sfm:1.1 .
# docker run -d -p 8080:8080 sfm:1.1
