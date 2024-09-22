# Example Spring Boot app with an Observation that publishes Prometheus histogram

This is an example Spring Boot application that demonstrates how you can create and publish Prometheus histogram from the `Timer` in Micrometer `Observation`. More information can be found in this article.

## Requirements

To run this sample, you will need to have the following software installed on your machine:
- Java (17 or later)
- Maven (3.8.3 or later) or Gradle (7.2 or later)
- Docker (20.10.8 or later)

## Running the Application

To run the application and start the Prometheus and Grafana containers, use the following command:

```bash
mvn spring-boot:run
```

Or, if you prefer to use Gradle, you can use the following command:

```bash
./gradlew bootRun
```

## Querying Metrics

To see the metrics exposed by the application along with their values, you can use the following command (or simply open the URL in a browser):

```bash
curl http://localhost:8080/actuator/prometheus
```

## Querying Metrics Using Prometheus

You can use Prometheus UI to query the metrics using PromQL. This UI can be accessed at the following endpoint:
    
```
http://localhost:9090/
```

## Viewing Metrics in Grafana

To view the metrics in Grafana, you can access Grafana using the following URL and navigate to the `Dashboard` section:

```
http://localhost:3000/
```

## Changing the Average Response Time and Request Count

To change the average response time and request count, you can use the following REST endpoints:
- http://localhost:8080/setResponseTimeMeanSeconds

For example, to set the average response time to 700 ms, you can use the following command:

```bash
curl -v -X POST http://localhost:8080/setMeanResponseTimeSeconds -H "Content-Type: application/json" -d 0.7
```
