FROM debian:buster
LABEL maintainer="Maurizio Franco --> m.franco@proximanetwork.it"
RUN apt-get update && \
apt-get install -y vim ssh net-tools locate nano curl && \
apt-get install -y openjdk-11-jre
RUN apt-get install -y tomcat9
RUN ln -sf /usr/share/tomcat9/etc /usr/share/tomcat9/conf 
RUN mkdir -p /usr/share/tomcat9/logs
COPY ./target/cerepro.hr.backend.war /usr/share/tomcat9/webapps/
ENV CATALINA_HOME /usr/share/tomcat9
ENV PATH $CATALINA_HOME/bin:$PATH
EXPOSE 8080
CMD ["catalina.sh", "run"]
