package ch.ethz.systems.netbench.xpt.sppifo.ports.TailDrop;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.ext.basic.IpHeader;

import java.util.concurrent.LinkedBlockingQueue;

public class TailDropOutputPort extends OutputPort {

    private final long maxQueueSizeBits;

    TailDropOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long maxQueueSizeBytes) {
        super(ownNetworkDevice, targetNetworkDevice, link, new LinkedBlockingQueue<Packet>());
        this.maxQueueSizeBits = maxQueueSizeBytes * 8L;
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

        // Tail-drop enqueue
        if (getBufferOccupiedBits() + ipHeader.getSizeBit() <= maxQueueSizeBits) {
            guaranteedEnqueue(packet);
        } else {
            SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED");
            if (ipHeader.getSourceId() == this.getOwnId()) {
                SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED_AT_SOURCE");
            }
        }
    }
}
