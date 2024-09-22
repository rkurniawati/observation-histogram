package org.gooddog.observationhistogram;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class Controller {
  private final TestService testService;

  public Controller(TestService testService) {
    this.testService = testService;
  }

  @PostMapping("/setMeanResponseTimeSeconds")
  public void setResponseTimeSecondsMean(@RequestBody double seconds) {
    testService.setServiceMeanResponseTimeSeconds(seconds);
  }

  @PostMapping("/setMeanNumberOfVisits")
  public void setMeanNumberOfVisits(@RequestBody double mean) {
    testService.setServiceVisitMeans(mean);
  }
}
