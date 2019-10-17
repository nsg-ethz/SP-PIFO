package ch.ethz.systems.netbench.ext.poissontraffic.flowsize;

import ch.ethz.systems.netbench.core.log.SimulationLogger;

public class pFabricWebSearchAlbert extends FlowSizeDistribution {

    public pFabricWebSearchAlbert() {
        super();
        SimulationLogger.logInfo("Flow planner flow size dist.", "pFabric data mining lower bound discrete");
    }

    @Override
    public long generateFlowSizeByte() {

        double outcome = independentRng.nextDouble();

        if (outcome >= 0.0 && outcome <= 0.15) {
            return 6*1460;
        } else if (outcome >= 0.15 && outcome <= 0.2){
            return 13*1460;
        } else if (outcome >= 0.2 && outcome <= 0.3) {
            return 19*1460;
        } else if (outcome >= 0.3 && outcome <= 0.4) {
            return 33*1460;
        } else if (outcome >= 0.4 && outcome <= 0.53) {
            return 53*1460;
        } else if (outcome >= 0.53 && outcome <= 0.6) {
            return 133*1460;
        } else if (outcome >= 0.6 && outcome <= 0.7) {
            return 667*1460;
        } else if (outcome >= 0.7 && outcome <= 0.8) {
            return 1333*1460;
        }else if (outcome >= 0.8 && outcome <= 0.9) {
            return 3333*1460;
        }else if (outcome >= 0.9 && outcome <= 0.97) {
            return 6667*1460;
        } else {
            return 20000*1460;
        }
    }
}
