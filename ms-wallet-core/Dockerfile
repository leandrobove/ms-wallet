FROM eclipse-temurin:17-jre-alpine

RUN mkdir /opt/app

COPY ./target/*.jar /opt/app/wallet-core.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/wallet-core.jar"]