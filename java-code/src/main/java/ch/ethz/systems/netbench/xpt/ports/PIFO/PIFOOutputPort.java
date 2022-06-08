package ch.ethz.systems.netbench.xpt.ports.PIFO;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.*;
import ch.ethz.systems.netbench.ext.basic.IpHeader;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;


public class PIFOOutputPort extends OutputPort {


    public PIFOOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long sizePackets) {
        super(ownNetworkDevice, targetNetworkDevice, link, new PIFOQueue(sizePackets, ownNetworkDevice));
    }

    /**
     * Enqueue the given packet.
     * There is no guarantee that the packet is actually sent,
     * as the queue buffer's limit might be reached. If the limit is reached,
     * the packet with lower priority (higher rank) is dropped.
     * @param packet    Packet instance
     */
    @Override
    public void enqueue(Packet packet) {

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

            // Enqueue to the PIFO queue
            PIFOQueue pq = (PIFOQueue) getQueue();
            FullExtTcpPacket droppedPacket = (FullExtTcpPacket)pq.offerPacket(packet);

            // Update buffer size with enqueued packet
            increaseBufferOccupiedBits(packet.getSizeBit());
            getLogger().logQueueState(pq.size(), getBufferOccupiedBits());

            if (droppedPacket != null) {

                // Update buffer size with dropped packet
                decreaseBufferOccupiedBits(droppedPacket.getSizeBit());
                getLogger().logQueueState(pq.size(), getBufferOccupiedBits());

                // Logging dropped packet
                SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED");
                IpHeader ipHeader = (IpHeader) droppedPacket;
                if (ipHeader.getSourceId() == this.getOwnId()) {
                    SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED_AT_SOURCE");
                }
            }
        }
    }


}
