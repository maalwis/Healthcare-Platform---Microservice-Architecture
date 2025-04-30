FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

COPY --link pom.xml mvnw ./
COPY --link .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY --link src ./src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:23-jre
WORKDIR /app

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

COPY --from=build /app/target/*.jar /app/app.jar

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8761

ENV SPRING_CONFIG_LOCATION=classpath:/application-docker.properties

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=80.0", "-jar", "/app/app.jar"]