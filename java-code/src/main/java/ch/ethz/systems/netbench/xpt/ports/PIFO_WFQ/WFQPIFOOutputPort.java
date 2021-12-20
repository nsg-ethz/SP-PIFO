package ch.ethz.systems.netbench.xpt.ports.PIFO_WFQ;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.*;
import ch.ethz.systems.netbench.ext.basic.IpHeader;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;


public class WFQPIFOOutputPort extends OutputPort {


    public WFQPIFOOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long sizePackets) {
        super(ownNetworkDevice, targetNetworkDevice, link, new WFQPIFOQueue(sizePackets, targetNetworkDevice.getIdentifier(), ownNetworkDevice.getIdentifier()));
    }

    /**
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
            WFQPIFOQueue pq = (WFQPIFOQueue) getQueue();
            Packet droppedPacket = pq.offerPacket(packet, this.getOwnId());

            // Update the size of the buffer with the size of packet enqueued
            increaseBufferOccupiedBits(packet.getSizeBit());
            getLogger().logQueueState(getQueue().size(), getBufferOccupiedBits());

            // Update the size of the buffer with the size of packet dropped
            if (droppedPacket != null) {
                decreaseBufferOccupiedBits(droppedPacket.getSizeBit());
                SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED");
                // Convert to IP packet
                IpHeader ipHeader = (IpHeader) droppedPacket;
                if (ipHeader.getSourceId() == this.getOwnId()) {
                    SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED_AT_SOURCE");
                }
            }
        }
    }
}

