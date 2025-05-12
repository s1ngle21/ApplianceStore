FROM openjdk:17-alpine
COPY target/ApplianceStore-0.0.1-SNAPSHOT.jar appliance-store-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "appliance-store-app.jar"]