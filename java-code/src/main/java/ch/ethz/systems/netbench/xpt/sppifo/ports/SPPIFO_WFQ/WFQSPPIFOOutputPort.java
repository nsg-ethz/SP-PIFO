package ch.ethz.systems.netbench.xpt.sppifo.ports.SPPIFO_WFQ;

import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.ext.basic.IpHeader;


public class WFQSPPIFOOutputPort extends OutputPort {

    public WFQSPPIFOOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long numberQueues, long sizePerQueuePackets) {
        super(ownNetworkDevice, targetNetworkDevice, link, new WFQSPPIFOQueue(numberQueues, sizePerQueuePackets));
    }

    /**
     * Enqueue the given packet.
     * Drops it if the queue is full (tail drop).
     *
     * @param packet    Packet instance
     */
    @Override
    public void enqueue(Packet packet) {

        // Convert to IP packet
        IpHeader ipHeader = (IpHeader) packet;

        // Mark congestion flag if size of the queue is too big
        if (getBufferOccupiedBits() >= 8L*48000) {
            ipHeader.markCongestionEncountered();
        }

        // Enqueue packet
        potentialEnqueue(packet);
    }
}
