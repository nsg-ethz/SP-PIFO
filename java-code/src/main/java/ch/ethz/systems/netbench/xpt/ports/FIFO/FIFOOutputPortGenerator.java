package ch.ethz.systems.netbench.xpt.ports.FIFO;


import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;

public class FIFOOutputPortGenerator extends OutputPortGenerator {

    private final long maxQueueSize;

    public FIFOOutputPortGenerator(long maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        SimulationLogger.logInfo("Port", "FIFO(maxQueueSize=" + maxQueueSize + ")");
    }

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
        return new FIFOOutputPort(ownNetworkDevice, towardsNetworkDevice, link, maxQueueSize);
    }

}
