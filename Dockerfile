FROM amazoncorretto:17-alpine AS Builder
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package
LABEL authors="Abdullah Turhan"
FROM amazoncorretto:17-alpine
WORKDIR app
COPY --from=Builder /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]