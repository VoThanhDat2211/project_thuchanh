#Dockerfile - cmd vao folder chua Dokerfile
FROM eclipse-temurin:17-jre-alpine
#copy jar from local into docker image
COPY target/project3_thuchanhweb-0.0.1-SNAPSHOT.jar project3_thuchanhweb-0.0.1-SNAPSHOT.jar
#command line to run jar
ENTRYPOINT ["java","-jar","/project3_thuchanhweb-0.0.1-SNAPSHOT.jar"]