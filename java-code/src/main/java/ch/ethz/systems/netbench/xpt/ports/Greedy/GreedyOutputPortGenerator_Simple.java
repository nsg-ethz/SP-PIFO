package ch.ethz.systems.netbench.xpt.ports.Greedy;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;

public class GreedyOutputPortGenerator_Simple extends OutputPortGenerator {

    private final long numberQueues;
    private final long sizePerQueuePackets;
    private final long adaptationPeriod;
    private final long maxRank;

    public GreedyOutputPortGenerator_Simple(long numberQueues, long sizePerQueuePackets, long adaptationPeriod, long maxRank) {
        this.numberQueues = numberQueues;
        this.sizePerQueuePackets = sizePerQueuePackets;
        this.adaptationPeriod = adaptationPeriod;
        this.maxRank = maxRank;
        SimulationLogger.logInfo("Port", "GreedySimple(numberQueues=" + numberQueues + ", sizePerQueuePackets=" + sizePerQueuePackets +
                ", adaptationPeriod=" + adaptationPeriod + ", maxRank=" + maxRank + ")");
    }

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
        return new GreedyOutputPort_Simple(ownNetworkDevice, towardsNetworkDevice, link, numberQueues, sizePerQueuePackets, adaptationPeriod, maxRank);
    }

}
