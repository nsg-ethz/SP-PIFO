package ch.ethz.systems.netbench.ext.poissontraffic.flowsize;

import ch.ethz.systems.netbench.core.log.SimulationLogger;

public class pFabricDataMiningAlbert extends FlowSizeDistribution {

    public pFabricDataMiningAlbert() {
        super();
        SimulationLogger.logInfo("Flow planner flow size dist.", "pFabric data mining lower bound discrete");
    }

    @Override
    public long generateFlowSizeByte() {

        double outcome = independentRng.nextDouble();

        if (outcome >= 0.0 && outcome <= 0.5) {
            return 1460;
        } else if (outcome >= 0.5 && outcome <= 0.6){
            return 2*1460;
        } else if (outcome >= 0.6 && outcome <= 0.7) {
            return 3*1460;
        } else if (outcome >= 0.7 && outcome <= 0.8) {
            return 7*1460;
        } else if (outcome >= 0.8 && outcome <= 0.9) {
            return 267*1460;
        } else if (outcome >= 0.9 && outcome <= 0.95) {
            return 2107*1460;
        } else if (outcome >= 0.95 && outcome <= 0.99) {
            return 66667*1460;
        } else {
            return 666667*1460;
        }
    }
}
