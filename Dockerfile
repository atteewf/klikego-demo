FROM tomcat:9-jdk11

RUN rm -rf /usr/local/tomcat/webapps/*

RUN apt-get update && apt-get install -y curl unzip zip

RUN curl -L -o /tmp/postgresql.jar https://jdbc.postgresql.org/download/postgresql-42.7.2.jar

COPY target/inscription-course.war /tmp/app.war

RUN mkdir -p /tmp/wardir && \
    cd /tmp/wardir && \
    unzip /tmp/app.war && \
    mkdir -p WEB-INF/lib && \
    cp /tmp/postgresql.jar WEB-INF/lib/ && \
    zip -r /usr/local/tomcat/webapps/ROOT.war . && \
    rm -rf /tmp/wardir /tmp/app.war

EXPOSE 8080
CMD ["catalina.sh", "run"]