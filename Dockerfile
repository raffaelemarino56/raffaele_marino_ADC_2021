FROM alpine/git
WORKDIR /app
RUN git clone https://github.com/raffaelemarino56/raffaele_marino_adc_2021.git

FROM maven:3.5-jdk-8-alpine
WORKDIR /app
COPY --from=0 /app/raffaele_marino_adc_2021 /app
RUN mvn package

FROM openjdk:8-jre-alpine
WORKDIR /app
ENV ID=0
ENV MASTER=127.0.0.1
COPY --from=1 /app/target/adc-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app

CMD /usr/bin/java -jar adc-0.0.1-SNAPSHOT-jar-with-dependencies.jar -id $ID -m $MASTER
