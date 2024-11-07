FROM alpine:latest

# mvn clean package
# docker scan --accept-license
# docker build --file docker-build-image.dockerfile -t ecommerce-bff-api-image --no-cache=false .

########################################
## Argumentos/Parametros
########################################
ARG build_job_name
ARG build_job_number
ARG build_date
ENV TZ=America/Sao_Paulo

########################################
## Network
########################################

#EXPOSE 443/tcp
EXPOSE 80/tcp

########################################
## Contexto do Container
########################################
RUN apk update && apk upgrade
RUN apk add vim wget zip git logtail paxctl

########################################
### Pastas
########################################
RUN mkdir -p /opt/app/
RUN mkdir -p /opt/app/java/
RUN mkdir -p /opt/app/logs/
RUN mkdir -p /opt/app/configs/

########################################
### Workdir
########################################

WORKDIR /opt/app/

########################################
### Configuracao do Java
########################################
RUN apk update --no-cache
RUN apk upgrade --no-cache
RUN apk add ca-certificates
RUN update-ca-certificates
RUN apk add --update coreutils && rm -rf /var/cache/apk/*
RUN apk add --update openjdk21 tzdata curl unzip bash
RUN apk add --no-cache nss
RUN rm -rf /var/cache/apk/*

########################################
### Volumes
########################################

VOLUME /opt/app/java/
VOLUME /opt/app/logs/
VOLUME /opt/app/configs/

########################################
### Adicionar Softwares
########################################

COPY target/app.jar /opt/app/java/

########################################
### Execution
########################################
#CMD ["/bin/bash","-c","tail -f /dev/null"]
CMD ["/bin/sh","-c","java -jar /opt/app/java/app.jar --spring.profiles.active=dev -DXms256mb -DXmx512mb"]