FROM moxm/java:1.8-full

RUN mkdir -p /shop-xxl-job-admin

WORKDIR /shop-xxl-job-admin

ARG JAR_FILE=target/shop-xxl-job-admin.jar

COPY ${JAR_FILE} app.jar

EXPOSE 5004

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar app.jar
