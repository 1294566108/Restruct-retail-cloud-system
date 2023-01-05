FROM moxm/java:1.8-full

RUN mkdir -p /shop-upms-biz

WORKDIR /shop-upms-biz

ARG JAR_FILE=target/shop-upms-biz.jar

COPY ${JAR_FILE} app.jar

EXPOSE 4000

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar app.jar
