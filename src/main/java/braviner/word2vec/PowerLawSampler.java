package braviner.word2vec;

import java.util.Random;

/**
 * Helper class to sample from the unigram distribution raised to a power.
 */
public class PowerLawSampler {

    private Random rng;

    private double[] powerSums;

    public PowerLawSampler(int[] counts, double power) {
        this(counts, power, new Random());
    }

    public PowerLawSampler(int[] counts, double power, Random rng) {
        this.rng = rng;

        powerSums = new double[counts.length];

        // Raise the counts to the specified power and sum them
        double sum = 0.0;
        for (int i=0; i<counts.length; i++) {
            sum += Math.pow(counts[i], power);
            powerSums[i] = sum;

        }
        // Normalize them
        for (int i=0; i<counts.length; i++) {
            powerSums[i] /= powerSums[powerSums.length-1];
        }

    }

    public int draw() {
        double x = rng.nextDouble();

        // Perform binary search to find the smallest index i
        // such that powerSums[i] >= x

        // Special case - lies below the first entry
        if (x <= powerSums[0]) return 0;

        // Invariant - the desired index should always be <= i1 and > i0
        // When i1 - i0 == 1 we stop.
        int i0 = 0;
        int i1 = powerSums.length-1;
        while (i1 - i0 > 1) {
            int j = (i1 - i0) / 2;
            int i2 = i0 + j;
            if (x <= powerSums[i2]) i1 = i2;
            else i0 = i2;
        }

        return i1;
    }

}
