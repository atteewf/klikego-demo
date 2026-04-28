FROM tomcat:9-jdk11

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/inscription-course.war /usr/local/tomcat/webapps/ROOT.war

RUN mkdir -p /usr/local/tomcat/lib && \
    cd /usr/local/tomcat/lib && \
    curl -O https://jdbc.postgresql.org/download/postgresql-42.7.2.jar

EXPOSE 8080

CMD ["catalina.sh", "run"]