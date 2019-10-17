package ch.ethz.systems.netbench.xpt.sppifo.ports.AFQ;

import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.ext.basic.IpHeader;


public class AFQOutputPort extends OutputPort {


    public AFQOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long numQueues, long perQueueCapacity, long bytesPerRound) {
        super(ownNetworkDevice, targetNetworkDevice, link, new AFQQueue(numQueues, perQueueCapacity, bytesPerRound, ownNetworkDevice.getIdentifier()));
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
