FROM eclipse-temurin:21-jre-alpine
COPY build/libs/invoicedoc-0.1.jar invoicedoc-0.1.jar
ENTRYPOINT [ "java", "-jar", "invoicedoc-0.1.jar" ]
EXPOSE 8080
RUN apk --update --no-cache add curl
HEALTHCHECK --interval=1m --timeout=30s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1
LABEL version="0.1" \
    description="Invoice microservice using MongoDB including Docker containers and health check test"