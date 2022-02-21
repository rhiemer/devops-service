ARG IMAGEM_BASE="registry.access.redhat.com/ubi8/openjdk-8"

FROM $IMAGEM_BASE

COPY target/*.jar /deployments/