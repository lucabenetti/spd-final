#
# Build stage
#
FROM maven:3.8.7 AS build
COPY src /src
COPY pom.xml pom.xml
RUN mvn -f pom.xml clean package

#
# Package stage
#
FROM openjdk:17
COPY --from=build /target/vacinacao-0.0.1-SNAPSHOT.jar /usr/local/lib/vacinacao.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/vacinacao.jar"]