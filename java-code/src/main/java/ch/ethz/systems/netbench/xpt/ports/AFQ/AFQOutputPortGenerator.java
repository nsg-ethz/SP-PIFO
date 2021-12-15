package ch.ethz.systems.netbench.xpt.ports.AFQ;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;

public class AFQOutputPortGenerator extends OutputPortGenerator {

    private final long numQueues;
    private final long perQueueCapacity;
    private final long bytesPerRound;

    public AFQOutputPortGenerator(long numQueues, long perQueueCapacity, long bytesPerRound) {
        this.numQueues = numQueues;
        this.perQueueCapacity = perQueueCapacity;
        this.bytesPerRound = bytesPerRound;
        SimulationLogger.logInfo("Port", "AFQ(numQueues=" + numQueues +  ", perQueueCapacity=" + perQueueCapacity + ", bytesPerRound=" + bytesPerRound + ")");
    }

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
        return new AFQOutputPort(ownNetworkDevice, towardsNetworkDevice, link, numQueues, perQueueCapacity, bytesPerRound);
    }

}