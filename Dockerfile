#stage 1
FROM gradle:jdk21 AS builder

WORKDIR /app

COPY ./build.gradle .
COPY ./settings.gradle .

COPY src ./src

RUN gradle build --no-daemon

#stage2
FROM openjdk:21-ea-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar discografia-1.jar

EXPOSE 443

CMD [ "java", "-jar","discografia-1.jar" ]