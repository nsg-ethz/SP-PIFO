package ch.ethz.systems.netbench.xpt.ports.PIFO_WFQ;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;

public class WFQPIFOOutputPortGenerator extends OutputPortGenerator {

    private final long sizePackets;

    public WFQPIFOOutputPortGenerator(long sizePackets) {
        this.sizePackets = sizePackets;
        SimulationLogger.logInfo("Port", "WFQPIFO(sizePackets=" + sizePackets + ")");
    }

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
        return new WFQPIFOOutputPort(ownNetworkDevice, towardsNetworkDevice, link, sizePackets);
    }

}