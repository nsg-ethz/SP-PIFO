package ch.ethz.systems.netbench.xpt.ports.PIFO;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;

public class PIFOOutputPortGenerator extends OutputPortGenerator {

    private final long sizePackets;

    public PIFOOutputPortGenerator(long sizePackets) {
        this.sizePackets = sizePackets;
        SimulationLogger.logInfo("Port", "PIFO(sizePackets=" + sizePackets + ")");
    }

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
        return new PIFOOutputPort(ownNetworkDevice, towardsNetworkDevice, link, sizePackets);
    }

}