FROM openjdk:11

COPY build/libs/project-0.0.3-SNAPSHOT.jar /project-0.0.3-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "/project-0.0.3-SNAPSHOT.jar"]