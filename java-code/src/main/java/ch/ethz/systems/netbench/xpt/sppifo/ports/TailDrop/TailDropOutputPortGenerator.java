package ch.ethz.systems.netbench.xpt.sppifo.ports.TailDrop;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;

public class TailDropOutputPortGenerator extends OutputPortGenerator {

    private final long maxQueueSizeBytes;

    public TailDropOutputPortGenerator(long maxQueueSizeBytes) {
        this.maxQueueSizeBytes = maxQueueSizeBytes;
        SimulationLogger.logInfo("Port", "TAILDROP(maxQueueSizeBytes=" + maxQueueSizeBytes + ")");
    }

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
        return new TailDropOutputPort(ownNetworkDevice, towardsNetworkDevice, link, maxQueueSizeBytes);
    }

}
