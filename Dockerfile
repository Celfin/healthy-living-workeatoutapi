# ---------- Stage 1: build the jar ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build

# Cache dependencies separately from source so code changes don't re-download the world
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests clean package

# ---------- Stage 2: runtime ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Run as a non-root user
RUN groupadd --system spring && useradd --system --gid spring spring

COPY --from=build /build/target/*.jar /app/app.jar
RUN chown -R spring:spring /app

USER spring
EXPOSE 8089

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
