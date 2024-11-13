FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./build/libs/band_budget-0.1.jar /app/band_budget-0.1.jar

CMD ["java", "-jar", "band_budget-0.1.jar"]