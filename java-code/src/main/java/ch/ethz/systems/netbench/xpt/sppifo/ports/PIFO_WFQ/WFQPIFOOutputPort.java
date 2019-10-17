package ch.ethz.systems.netbench.xpt.sppifo.ports.PIFO_WFQ;

import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.ext.basic.IpHeader;


public class WFQPIFOOutputPort extends OutputPort {


    public WFQPIFOOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long sizePackets) {
        super(ownNetworkDevice, targetNetworkDevice, link, new WFQPIFOQueue(sizePackets, targetNetworkDevice.getIdentifier(), ownNetworkDevice.getIdentifier()));
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
        pushWFQ(packet);
    }
}
