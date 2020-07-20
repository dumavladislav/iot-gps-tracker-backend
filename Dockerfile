FROM openjdk:8-jdk-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

COPY ./target/ /usr/src/iot-gps-tracker-backend/
WORKDIR /usr/src/iot-gps-tracker-backend

RUN apk add tzdata
RUN cp /usr/share/zoneinfo/Europe/Moscow /etc/localtime
RUN echo "Europe/Moscow" >  /etc/timezone

ENTRYPOINT ["java", "-jar", "./iot-gps-tracker-backend-0.0.1-SNAPSHOT.jar"]