FROM openjdk:alpine
MAINTAINER "Sumanth Chinthagunta"

RUN apk add --update curl \
    && rm -rf /var/cache/apk/*

# Path for logs
RUN mkdir -p /tmp/spring-flow/logs

VOLUME /tmp/spring-flow/logs

# Port range for streams
EXPOSE 7000-7010

# Download spring cloud data flow server
RUN wget -O app.jar http://repo.spring.io/milestone/org/springframework/cloud/spring-cloud-dataflow-server-local/1.3.0.M3/spring-cloud-dataflow-server-local-1.3.0.M3.jar

# Set tmp dir for logs
ENV JAVA_OPTS="-Djava.io.tmpdir=/tmp/spring-flow/logs"

# Run application
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ./app.jar" ]
