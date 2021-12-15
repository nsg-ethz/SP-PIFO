package ch.ethz.systems.netbench.xpt.voijslav_and_sppifo.tcp.lstftcp;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.TransportLayer;
import ch.ethz.systems.netbench.core.run.infrastructure.TransportLayerGenerator;

public class LstfTcpTransportLayerGenerator extends TransportLayerGenerator {

    private String rankDistribution;
    private long rankBound;

    public LstfTcpTransportLayerGenerator(String rankDistribution, long rankBound) {
        SimulationLogger.logInfo("Transport layer", "LSTF TCP");
        this.rankDistribution = rankDistribution;
        this.rankBound = rankBound;
    }

    @Override
    public TransportLayer generate(int identifier) {
        return new LstfTcpTransportLayer(identifier, rankDistribution, rankBound);
    }

}