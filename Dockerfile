FROM adoptopenjdk/openjdk11:alpine-jre
##when build in local uncomment the above
##ARG JAR_FILE=./target/cerepro.hr.backend.jar
ARG JAR_FILE=cerepro.hr.backend.jar
WORKDIR /opt/app
COPY ${JAR_FILE} cerepro.hr.backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","cerepro.hr.backend.jar"]
