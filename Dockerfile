FROM openjdk:19 as builder
WORKDIR /opt/app

COPY ./.mvn/ .mvn
COPY ./mvnw ./pom.xml ./

COPY ./src/main/resources/server.crt /opt/app/server.crt

RUN ./mvnw protobuf:compile
COPY ./src ./src
RUN ./mvnw clean install
###
FROM openjdk:19
WORKDIR /opt/app

COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
