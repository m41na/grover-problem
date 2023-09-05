FROM openjdk:17-alpine
LABEL authors="maina"
USER root

ENV GRADLE_VERSION 8.3
RUN apk --update add openssl wget unzip && rm -rf /var/cache/apk/*
RUN echo "Downloading Gradle" \
    && wget --no-verbose --output-document=/tmp/gradle-8.3-bin.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"

RUN echo "Installing Gradle" \
    && unzip -d /opt/gradle /tmp/gradle-8.3-bin.zip \
    && rm /tmp/gradle-8.3-bin.zip

ENV GRADLE_HOME="/opt/gradle/gradle-8.3"
ENV PATH="${PATH}:${GRADLE_HOME}/bin"
RUN ln -s "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle

USER 1000

RUN echo "Testing Gradle installation" \
    && gradle --version

