package org.gooddog.observationhistogram.generators;

import lombok.Getter;
import org.apache.commons.math3.distribution.PoissonDistribution;

public class PoissonRandomGenerator {
  @Getter private double mean;
  private PoissonDistribution poissonDistribution;

  public PoissonRandomGenerator(double mean) {
    this.mean = mean;
    this.poissonDistribution = new PoissonDistribution(mean);
  }

  public int poissonRandom() {
    return poissonDistribution.sample();
  }

  public void setMean(double mean) {
    this.mean = mean;
    this.poissonDistribution = new PoissonDistribution(mean);
  }
}
