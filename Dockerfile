FROM library/eclipse-temurin:17.0.10_7-jdk-alpine AS builder

RUN jlink --add-modules java.base,java.desktop,java.instrument,java.naming,java.scripting,java.security.jgss,java.sql,java.sql.rowset,java.xml,jdk.crypto.ec,jdk.jdwp.agent,jdk.jfr,jdk.management,jdk.management.agent,jdk.management.jfr,jdk.unsupported \
          --strip-debug \
          --output /javaruntime

WORKDIR /layers

COPY backend/target/rik*.jar rik.jar

RUN java -Djarmode=layertools -jar rik.jar extract

FROM library/alpine:3.19.1

COPY --from=builder /javaruntime/ /opt/java/openjdk/

RUN ln -s /opt/java/openjdk/bin/java /usr/local/bin/java && \
    addgroup -S rik && \
    adduser -S rik -G rik && \
    mkdir /var/log/rik && \
    chown rik: /var/log/rik && \
    chmod u+w /var/log/rik

WORKDIR /app

COPY --from=builder layers/dependencies/ ./
COPY --from=builder layers/snapshot-dependencies/ ./
COPY --from=builder layers/spring-boot-loader/ ./
COPY --from=builder layers/application/ ./

ENV LOG_BASE_PATH=/var/log/rik/

USER rik

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} org.springframework.boot.loader.launch.JarLauncher"]
