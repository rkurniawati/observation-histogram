package org.gooddog.observationhistogram;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import java.time.Duration;
import java.util.stream.IntStream;
import lombok.NonNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
class TestServiceMeterConfig implements MeterFilter {
  private static final double[] SLO =
      IntStream.rangeClosed(1, 15)
          .mapToDouble(i -> Duration.ofMillis(i * 100L).toNanos())
          .toArray();
  private static final double MAX_RESPONSE_TIME = Duration.ofMillis(1500).toNanos();
  private static final double MIN_RESPONSE_TIME = Duration.ofMillis(1).toNanos();

  @Override
  public DistributionStatisticConfig configure(
      final @NonNull Meter.Id id, final @NonNull DistributionStatisticConfig config) {
    if (id.getName().startsWith("test.service") && id.getType() == Meter.Type.TIMER) {
      return DistributionStatisticConfig.builder()
          .maximumExpectedValue(MAX_RESPONSE_TIME)
          .minimumExpectedValue(MIN_RESPONSE_TIME)
          .serviceLevelObjectives(SLO)
          .build()
          .merge(config);
    }
    return config;
  }
}
