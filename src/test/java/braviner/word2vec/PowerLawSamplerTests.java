package braviner.word2vec;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class PowerLawSamplerTests {

    @Test
    public void TestSampleUnitPower() {

        class FakeRNG extends Random {
            private double[] schedule = new double[] {0.1, 0.3, 0.7, 0.8};
            private int i=0;

            @Override
            public double nextDouble() {
                return schedule[i++];
            }
        }

        PowerLawSampler sampler = new PowerLawSampler(new int[] {1, 2, 0, 1}, 1.0, new FakeRNG());

        Assert.assertEquals(0, sampler.draw());
        Assert.assertEquals(1, sampler.draw());
        Assert.assertEquals(1, sampler.draw());
        Assert.assertEquals(3, sampler.draw());
    }

    @Test
    public void TestSamplePower() {

        class FakeRNG extends Random {
            private double[] schedule = new double[] {0.1, 0.3, 0.7, 0.8};
            private int i=0;

            @Override
            public double nextDouble() {
                return schedule[i++];
            }
        }

        PowerLawSampler sampler = new PowerLawSampler(new int[] {1, 4, 0, 1}, 0.5, new FakeRNG());

        Assert.assertEquals(0, sampler.draw());
        Assert.assertEquals(1, sampler.draw());
        Assert.assertEquals(1, sampler.draw());
        Assert.assertEquals(3, sampler.draw());
    }
}
