FROM amazoncorretto:17

WORKDIR /app

COPY target/*.jar /app/wallet-core.jar

EXPOSE 8080

CMD [ "java", "-jar",  "wallet-core.jar"]