package ch.ethz.systems.netbench.xpt.voijslav_and_sppifo.tcp.lstftcp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;

/**
 * RandomNumberGenerator creates random numbers according to one of the following distributions:
 * Poisson, Exponential, Geometric, Pareto, ParetoBounded, Uniform or Constant
 *
 * The method getRandom() will return the next random value.
 * It can use a provided stream Random r, if needed, or use the default stream.
 *
 * For most of the distributions a single distribution parameter is needed.
 * USAGE: double rand = Exponential.getRandom(0.25);
 *
 * When the wrong number of parameters is passed, IllegalArgumentException is thrown.
 * Code extracted from: http://www.cs.technion.ac.il/~azlotnik/RandomNumberGenerator.java.html
 */
public enum RandomNumberGenerator {
    Constant {
        @Override
        public double getRandom(Random r, double N) {
            return N;
        }
    },
    Exponential {
        @Override
        public double getRandom(Random r, double p) {
            return -(Math.log(r.nextDouble()) / p);
        }
    },
    Geometric {
        @Override
        public double getRandom(Random r, double geoSeed) {
            double p = 1.0 / ((double) geoSeed);
            return (int) (Math.ceil(Math.log(r.nextDouble()) / Math.log(1.0 - p)));
        }
    },
    Pareto {
        @Override
        public double getRandom(Random r, double alpha, double xM) {
            double v = r.nextDouble();
            while (v == 0) {
                v = r.nextDouble();
            }

            return xM / Math.pow(v, 1.0 / alpha);
        }
    },
    ParetoBounded {
        @Override
        public double getRandom(Random r, double alpha, double L, double H) {
            double u = r.nextDouble();
            while (u == 0) {
                u = r.nextDouble();
            }

            double x = -(u * Math.pow(H, alpha) - u * Math.pow(L, alpha) - Math.pow(H, alpha)) /
                    (Math.pow(H * L, alpha));
            return Math.pow(x, -1.0 / alpha);
        }
    },
    Poisson {
        @Override
        public double getRandom(Random r, double lambda) {
            double L = Math.exp(-lambda);
            int k = 0;
            double p = 1.0;
            do {
                k++;
                p = p * r.nextDouble();
            } while (p > L);

            return k - 1;
        }
    },
    Uniform {
        @Override
        public double getRandom(Random r, double p) {
            return r.nextDouble() * p;
        }
    };

    public double getRandom(double p) throws IllegalArgumentException {
        return getRandom(defaultR, p);
    }

    public double getRandom(double a, double b) throws IllegalArgumentException {
        return getRandom(defaultR, a, b);
    }

    public double getRandom(double a, double b, double c) throws IllegalArgumentException {
        return getRandom(defaultR, a, b, c);
    }

    public double getRandom(Random r, double p) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    public double getRandom(Random r, double a, double b) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    public double getRandom(Random r, double a, double b, double c) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    public static final Random defaultR = new Random();

}