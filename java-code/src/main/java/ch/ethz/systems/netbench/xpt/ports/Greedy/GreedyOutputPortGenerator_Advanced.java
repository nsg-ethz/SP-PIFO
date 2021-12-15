package ch.ethz.systems.netbench.xpt.ports.Greedy;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;

public class GreedyOutputPortGenerator_Advanced extends OutputPortGenerator {

    private final long numberQueues;
    private final long sizePerQueuePackets;
    private final String initialization;
    private final String fixQueueBounds;

    public GreedyOutputPortGenerator_Advanced(long numberQueues, long sizePerQueuePackets, String initialization, String fixQueueBounds) {
        this.numberQueues = numberQueues;
        this.sizePerQueuePackets = sizePerQueuePackets;
        this.initialization = initialization;
        this.fixQueueBounds = fixQueueBounds;
        SimulationLogger.logInfo("Port", "GreedyAdvanced(numberQueues=" + numberQueues + ", sizePerQueuePackets=" + sizePerQueuePackets +
                ", fixQueueBounds =" + fixQueueBounds + ")");
    }

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
        return new GreedyOutputPort_Advanced(ownNetworkDevice, towardsNetworkDevice, link, numberQueues, sizePerQueuePackets, initialization, fixQueueBounds);
    }

}
