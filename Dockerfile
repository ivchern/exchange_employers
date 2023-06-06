
FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN dos2unix mvnw
#RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]