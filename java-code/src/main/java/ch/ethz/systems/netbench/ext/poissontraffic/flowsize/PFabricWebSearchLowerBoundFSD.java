package ch.ethz.systems.netbench.ext.poissontraffic.flowsize;

import ch.ethz.systems.netbench.core.log.SimulationLogger;

import java.util.Random;

/**
 * pFabric (Alizadeh, 2016) web search flow size distribution.
 *
 * Size (byte)  Idx     Culc. prob.
 * 0            1       0
 * 10000        2       0.15
 * 20000        3       0.2
 * 30000        4       0.3
 * 50000        5       0.4
 * 80000        6       0.53
 * 200000       7       0.6
 * 1e+06        8       0.7
 * 2e+06        9       0.8
 * 5e+06        10      0.9
 * 1e+07        11      0.97
 * 3e+07        12      1
 *
 * Expected flow size (lower bound):
 * 0.15*1+0.05*10000+0.1*20000+0.1*30000+0.13*50000+0.07*80000
   +0.1*200000+0.1*1000000+0.1*2000000+0.07*5000000+0.03*10000000
 * =
 * 987600.15 bytes
 * At 10 Gbps would take 0.79ms
 *
 * NOTE: the 1 is because non-empty flows don't make any sense
 */
public class PFabricWebSearchLowerBoundFSD extends FlowSizeDistribution {

    public PFabricWebSearchLowerBoundFSD() {
        super();
        SimulationLogger.logInfo("Flow planner flow size dist.", "pFabric web search lower bound discrete");
    }

    @Override
    public long generateFlowSizeByte() {

        double outcome = independentRng.nextDouble();

        if (outcome >= 0.0 && outcome <= 0.15) {
            return 1;
        } else if (outcome >= 0.15 && outcome <= 0.2) {
            return 10000;
        } else if (outcome >= 0.2 && outcome <= 0.3) {
            return 20000;
        } else if (outcome >= 0.3 && outcome <= 0.4) {
            return 30000;
        } else if (outcome >= 0.4 && outcome <= 0.53) {
            return 50000;
        } else if (outcome >= 0.53 && outcome <= 0.6) {
            return 80000;
        } else if (outcome >= 0.6 && outcome <= 0.7) {
            return 200000;
        } else if (outcome >= 0.7 && outcome <= 0.8) {
            return 1000000;
        } else if (outcome >= 0.8 && outcome <= 0.9) {
            return 2000000;
        } else if (outcome >= 0.9 && outcome <= 0.97) {
            return 5000000;
        } else { // outcome >= 0.97 && outcome <= 1.0
            return 10000000;
        }

        /*
        if (outcome >= 0.0 && outcome <= 0.15) {
            return 1;
        } else if (outcome >= 0.15 && outcome <= 0.16) {
            return 10000;
        } else if (outcome >= 0.16 && outcome <= 0.17) {
            return 12000;
        } else if (outcome >= 0.17 && outcome <= 0.18) {
            return 14000;
        } else if (outcome >= 0.18 && outcome <= 0.19) {
            return 16000;
        } else if (outcome >= 0.19 && outcome <= 0.20) {
            return 18000;
        } else if (outcome >= 0.20 && outcome <= 0.21) {
            return 20000;
        } else if (outcome >= 0.21 && outcome <= 0.22) {
            return 21000;
        } else if (outcome >= 0.22 && outcome <= 0.23) {
            return 22000;
        } else if (outcome >= 0.23 && outcome <= 0.24) {
            return 23000;
        } else if (outcome >= 0.24 && outcome <= 0.25) {
            return 24000;
        } else if (outcome >= 0.25 && outcome <= 0.26) {
            return 25000;
        } else if (outcome >= 0.26 && outcome <= 0.27) {
            return 26000;
        } else if (outcome >= 0.27 && outcome <= 0.28) {
            return 27000;
        } else if (outcome >= 0.28 && outcome <= 0.29) {
            return 28000;
        } else if (outcome >= 0.29 && outcome <= 0.30) {
            return 29000;
        } else if (outcome >= 0.30 && outcome <= 0.40) {
            return 30000;
        } else if (outcome >= 0.40 && outcome <= 0.53) {
            return 50000;
        } else if (outcome >= 0.53 && outcome <= 0.60) {
            return 80000;
        } else if (outcome >= 0.60 && outcome <= 0.61) {
            return 200000;
        } else if (outcome >= 0.61 && outcome <= 0.62) {
            return 300000;
        } else if (outcome >= 0.62 && outcome <= 0.63) {
            return 400000;
        } else if (outcome >= 0.63 && outcome <= 0.64) {
            return 450000;
        } else if (outcome >= 0.64 && outcome <= 0.65) {
            return 500000;
        } else if (outcome >= 0.65 && outcome <= 0.66) {
            return 550000;
        } else if (outcome >= 0.66 && outcome <= 0.67) {
            return 600000;
        } else if (outcome >= 0.67 && outcome <= 0.68) {
            return 700000;
        } else if (outcome >= 0.68 && outcome <= 0.69) {
            return 800000;
        } else if (outcome >= 0.69 && outcome <= 0.70) {
            return 900000;
        } else if (outcome >= 0.70 && outcome <= 0.71) {
            return 1000000;
        } else if (outcome >= 0.71 && outcome <= 0.72) {
            return 1100000;
        } else if (outcome >= 0.72 && outcome <= 0.73) {
            return 1200000;
        } else if (outcome >= 0.73 && outcome <= 0.74) {
            return 1300000;
        } else if (outcome >= 0.74 && outcome <= 0.75) {
            return 1400000;
        } else if (outcome >= 0.75 && outcome <= 0.76) {
            return 1500000;
        } else if (outcome >= 0.76 && outcome <= 0.77) {
            return 1600000;
        } else if (outcome >= 0.77 && outcome <= 0.78) {
            return 1700000;
        } else if (outcome >= 0.78 && outcome <= 0.79) {
            return 1800000;
        } else if (outcome >= 0.79 && outcome <= 0.80) {
            return 1900000;
        } else if (outcome >= 0.80 && outcome <= 0.81) {
            return 2000000;
        } else if (outcome >= 0.81 && outcome <= 0.82) {
            return 2300000;
        } else if (outcome >= 0.82 && outcome <= 0.83) {
            return 2600000;
        } else if (outcome >= 0.83 && outcome <= 0.84) {
            return 2900000;
        } else if (outcome >= 0.84 && outcome <= 0.85) {
            return 3100000;
        } else if (outcome >= 0.85 && outcome <= 0.86) {
            return 3400000;
        } else if (outcome >= 0.86 && outcome <= 0.87) {
            return 3700000;
        } else if (outcome >= 0.87 && outcome <= 0.88) {
            return 4000000;
        } else if (outcome >= 0.88 && outcome <= 0.89) {
            return 4300000;
        } else if (outcome >= 0.89 && outcome <= 0.90) {
            return 4600000;
        } else if (outcome >= 0.90 && outcome <= 0.97) {
            return 5000000;
        } else if (outcome >= 0.97 && outcome <= 0.98) {
            return 6000000;
        } else if (outcome >= 0.98 && outcome <= 0.99) {
            return 7000000;
        } else {
            return 10000000;
        }
        */
    }

}
