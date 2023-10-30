FROM openjdk:19 as builder
WORKDIR /opt/app

COPY ./exchange_employers/.mvn/ .mvn
COPY ./exchange_employers/mvnw ./exchange_employers/pom.xml ./

COPY ./exchange_employers/src/main/resources/server.crt /opt/app/server.crt

RUN ./mvnw protobuf:compile
COPY ./exchange_employers/src ./src
RUN ./mvnw clean install

FROM openjdk:19
WORKDIR /opt/app

COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
