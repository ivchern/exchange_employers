#!/bin/bash
export TERM=xterm
dos2unix mvnw
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" &

while true
do
   watch -d -t -g "ls -lR . | sha1sum" && ./mvnw compile
done
