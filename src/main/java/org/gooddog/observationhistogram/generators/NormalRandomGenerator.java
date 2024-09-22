package org.gooddog.observationhistogram.generators;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.random.RandomDataGenerator;

public class NormalRandomGenerator {
  @Setter @Getter private double mean;
  private final double stddev;
  private final RandomDataGenerator gaussianRandomGenerator;

  public NormalRandomGenerator(double mean, double stddev) {
    this.mean = mean;
    this.stddev = stddev;
    this.gaussianRandomGenerator = new RandomDataGenerator();
  }

  public double normalRandom() {
    return gaussianRandomGenerator.nextGaussian(mean, stddev);
  }
}
