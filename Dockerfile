ARG JAVA_VERSION=21

FROM eclipse-temurin:${JAVA_VERSION}-jdk as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon build

FROM eclipse-temurin:${JAVA_VERSION}-jre

COPY --from=BUILD /src/app/build/distributions/app*.tar app.tar

RUN mkdir app
RUN tar -xf app.tar --strip-components=1 -C app/

WORKDIR /app

CMD ["bin/app"]
