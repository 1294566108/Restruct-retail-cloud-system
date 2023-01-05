FROM moxm/java:1.8-full

RUN mkdir -p /shop-monitor

WORKDIR /shop-monitor

ARG JAR_FILE=target/shop-monitor.jar

COPY ${JAR_FILE} app.jar

EXPOSE 5001

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar app.jar
