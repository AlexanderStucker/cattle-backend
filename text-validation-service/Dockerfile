FROM quay.io/quarkus/ubi-quarkus-native-image:latest

WORKDIR /app

COPY target/*-runner /app/application

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
