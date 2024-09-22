package org.gooddog.observationhistogram;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class ScheduledTrigger {
  private final TestService testService;

  public ScheduledTrigger(TestService testService) {
    this.testService = testService;
  }

  @Scheduled(fixedRate = 30_000)
  public void trigger() {
    testService.serviceApi1();
  }
}
