package org.gooddog.observationhistogram;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import java.time.Duration;
import java.util.stream.IntStream;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class TestServiceMeterConfig implements MeterFilter {
  private static final double MAX_RESPONSE_TIME = Duration.ofMillis(1500).toNanos();

  private final String histogramConfig;

  public TestServiceMeterConfig(@Value("${test.service.histogram.config}") String histogramConfig) {
    this.histogramConfig = histogramConfig;
  }

  @Override
  public DistributionStatisticConfig configure(
      final @NonNull Meter.Id id, final @NonNull DistributionStatisticConfig config) {

    // only configure the metric for the timer object of the observation
    if (id.getName().startsWith("test.service") && id.getType() == Meter.Type.TIMER) {

      switch (histogramConfig) {
        case "publish-histogram" -> {
          return DistributionStatisticConfig.builder()
              .percentilesHistogram(true)
              .build()
              .merge(config);
        }
        case "publish-histogram-set-max" -> {
          return DistributionStatisticConfig.builder()
              .maximumExpectedValue(MAX_RESPONSE_TIME)
              .percentilesHistogram(true)
              .build()
              .merge(config);
        }
        case "use-slo" -> {
          // slo array contains the following bucket boundaries:
          //  100ms, 200ms, ... , 1400ms, 1500ms
          double[] slo =
              IntStream.rangeClosed(1, 15)
                  .mapToDouble(i -> Duration.ofMillis(i * 100L).toNanos())
                  .toArray();

          return DistributionStatisticConfig.builder()
              .maximumExpectedValue(MAX_RESPONSE_TIME)
              .serviceLevelObjectives(slo)
              .build()
              .merge(config);
        }
      }
    }
    return config;
  }
}
