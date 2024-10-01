package org.gooddog.observationhistogram;

import io.micrometer.observation.annotation.Observed;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.gooddog.observationhistogram.generators.NormalRandomGenerator;
import org.gooddog.observationhistogram.generators.PoissonRandomGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
  private final NormalRandomGenerator serviceResponseTimeSecondsGenerator;
  private final PoissonRandomGenerator serviceVisitsGenerator;

  public TestService(
      @Value("${spring.application.test.service.mean.response.time.seconds}")
          double serviceMeanResponseTimeSeconds,
      @Value("${spring.application.test.service.stddev.response.time.seconds}")
          double servicestddevResponseTimeSeconds,
      @Value("${spring.application.test.service.mean.visits}") double serviceVisitMeans) {

    this.serviceResponseTimeSecondsGenerator =
        new NormalRandomGenerator(serviceMeanResponseTimeSeconds, servicestddevResponseTimeSeconds);
    this.serviceVisitsGenerator = new PoissonRandomGenerator(serviceVisitMeans);
  }

  private void sleep(Duration duration) {
    try {
      Thread.sleep(duration.toMillis());
    } catch (InterruptedException e) {
      log.error("Sleep interrupted", e);
    }
  }

  @Observed(name = "test.service.response.time.seconds")
  public void serviceApi() {
    long responseTimeMillis = (long) (serviceResponseTimeSecondsGenerator.normalRandom() * 1_000);
    log.debug("sleeping for {} milliseconds", responseTimeMillis);
    sleep(Duration.ofMillis(responseTimeMillis));
  }

  public void setServiceMeanResponseTimeSeconds(double newMean) {
    serviceResponseTimeSecondsGenerator.setMean(newMean);
  }

  public void setServiceVisitMeans(double mean) {
    serviceVisitsGenerator.setMean(mean);
  }
}
