package ch.ethz.systems.netbench.ext.basic;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.network.*;
import ch.ethz.systems.netbench.core.log.SimulationLogger;
import java.util.concurrent.LinkedBlockingQueue;

public class EcnTailDropOutputPort extends OutputPort {

    private final long ecnThresholdKBits;
    private final long maxQueueSizeBits;

    EcnTailDropOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long maxQueueSizeBytes, long ecnThresholdKBytes) {
        super(ownNetworkDevice, targetNetworkDevice, link, new LinkedBlockingQueue<Packet>());
        this.maxQueueSizeBits = maxQueueSizeBytes * 8L;
        this.ecnThresholdKBits = ecnThresholdKBytes * 8L;
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
        if (getBufferOccupiedBits() >= ecnThresholdKBits) {
            ipHeader.markCongestionEncountered();
        }

        // Tail-drop enqueue
        if (getBufferOccupiedBits() + ipHeader.getSizeBit() <= maxQueueSizeBits) {

            // If it is not sending, then the queue is empty at the moment,
            // so this packet can be immediately send
            if (!getIsSending()) {

                // Link is now being utilized
                getLogger().logLinkUtilized(true);

                // Add event when sending is finished
                Simulator.registerEvent(new PacketDispatchedEvent(
                        (long)((double)packet.getSizeBit() / getLink().getBandwidthBitPerNs()),
                        packet,
                        this
                ));

                // It is now sending again
                setIsSending();

            } else { // If it is still sending, the packet is added to the queue, making it non-empty
                getQueue().add(packet);
                increaseBufferOccupiedBits(packet.getSizeBit());
                getLogger().logQueueState(getQueue().size(), getBufferOccupiedBits());
            }

        } else {
            SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED");
            if (ipHeader.getSourceId() == this.getOwnId()) {
                SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED_AT_SOURCE");
            }
        }

    }

}
