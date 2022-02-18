ARG IMAGEM_BASE_MAVEN="maven:3.8.4-jdk-11"
ARG IMAGEM_BASE="registry.access.redhat.com/ubi8/openjdk-8"


FROM $IMAGEM_BASE_MAVEN AS builder

ARG URL_MVN=""
ARG REPOSITORY_ID=""
ARG USUARIO_MVN=""
ARG SENHA_MVN=""
ARG SETTINGS="/build/settings.xml"
ARG LOCAL_REPOSITORY="/usr/share/maven/ref/repository"

RUN mkdir -p /build/src

COPY pom.xml /build/
COPY src/ /build/src/
COPY scripts/ /scripts/
RUN find /scripts -type f  \ 
      -exec sh -c 'tr -d "\r" < "{}" > "{}".new && mv "{}".new "{}"' -- {} \; \    
      -exec chmod +x {} \;

ENV URL_MVN="$URL_MVN" \
    REPOSITORY_ID="$REPOSITORY_ID" \
    USUARIO_MVN="$USUARIO_MVN" \
    SENHA_MVN="$SENHA_MVN" \
    SETTINGS="$SETTINGS" \
    LOCAL_REPOSITORY="$LOCAL_REPOSITORY"

RUN /scripts/build/maven-settings.sh --settings "/build/settings.xml"

WORKDIR /build

RUN mvn -B --settings "$SETTINGS" clean package spring-boot:repackage

FROM $IMAGEM_BASE

COPY --from=builder /build/target/*.jar /deployments/