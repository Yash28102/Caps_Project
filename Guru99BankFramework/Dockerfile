FROM selenium/standalone-chrome:latest

USER root

WORKDIR /app

RUN apt-get update && \
    apt-get install -y maven openjdk-21-jdk && \
    apt-get clean

COPY . .

RUN mvn clean install -DskipTests

CMD ["mvn", "clean", "test", "-DrunMode=docker", "-Dbrowser=chrome"]
