FROM maven:3.6.0-jdk-11 AS build
COPY . ./
RUN mvn clean package

FROM openjdk:11
COPY --from=build target/*.jar ./app.jar
COPY src/main/resources/storage/product-storage src/main/resources/storage/product-storage
EXPOSE 8081
ENTRYPOINT ["java","-jar","./app.jar"]