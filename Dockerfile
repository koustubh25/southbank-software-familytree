FROM maven:3.5.4-jdk-8-alpine
RUN mkdir -p /tmp/familytree/src
COPY pom.xml /tmp/familytree/.
COPY src /tmp/familytree/src/.
EXPOSE 8080
RUN cd /tmp/familytree && mvn clean install
ENTRYPOINT ["/bin/bash","-c","cd /tmp/familytree && \
                               java -jar target/familytree-1.0.jar"]