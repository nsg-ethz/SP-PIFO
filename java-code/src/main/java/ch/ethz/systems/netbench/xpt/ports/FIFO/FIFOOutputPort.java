package ch.ethz.systems.netbench.xpt.ports.FIFO;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.*;
import ch.ethz.systems.netbench.ext.basic.IpHeader;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;
import java.util.concurrent.LinkedBlockingQueue;

public class FIFOOutputPort extends OutputPort {

    private final long maxQueueSize;

    FIFOOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long maxQueueSize) {
        super(ownNetworkDevice, targetNetworkDevice, link, new LinkedBlockingQueue<Packet>());
        this.maxQueueSize = maxQueueSize;
    }

    /**
     * Enqueue the given packet.
     * Drops it if the queue is full (tail drop).
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
                    packet.getSizeBit() / getLink().getBandwidthBitPerNs(),
                    packet,
                    this
            ));

            // It is now sending again
            setIsSending();

            // Log packet for debugging
            if(SimulationLogger.hasPacketsTrackingEnabled()){
                FullExtTcpPacket pk = (FullExtTcpPacket)packet;
                SimulationLogger.logPacket("Time: " + Simulator.getCurrentTime() + " => Packet sent (no queue): SeqNo: " + pk.getSequenceNumber() + ", ACKNo: " + pk.getAcknowledgementNumber() + ", Priority: "+ pk.getPriority());
            }

        } else { // If it is still sending, the packet is added to the queue (if there is space)

            // Log packet for debugging
            if(SimulationLogger.hasPacketsTrackingEnabled()) {
                FullExtTcpPacket pk = (FullExtTcpPacket)packet;
                SimulationLogger.logPacket("Time: " + Simulator.getCurrentTime() + " => Packet enqueued: SeqNo: " + pk.getSequenceNumber() + ", ACKNo: " + pk.getAcknowledgementNumber() + ", Priority: " + pk.getPriority());
            }

            // We tag the enqueue time to the packet, before offering it to PIFO
            FullExtTcpPacket p = (FullExtTcpPacket) packet;
            p.setEnqueueTime(Simulator.getCurrentTime());

            // Tail-drop enqueue
            if (getQueueSize() <= maxQueueSize-1) {

                // Enqueue to the FIFO queue
                getQueue().add(packet);

                // Update buffer size with enqueued packet
                increaseBufferOccupiedBits(packet.getSizeBit());
                getLogger().logQueueState(getQueue().size(), getBufferOccupiedBits());

            } else {

                /* Debug drops */
                String message = "FIFO Queue: [";
                Object[] contentFIFO = getQueue().toArray();
                for (int j = 0; j<contentFIFO.length; j++){
                    message = message + ((FullExtTcpPacket)contentFIFO[j]).getPriority() + "(" + ((FullExtTcpPacket)contentFIFO[j]).getEnqueueTime() + ") , ";
                }
                message = message + "]\n";
                message = message + "Packet dropped: " + p.getPriority() + "(" + p.getEnqueueTime() + ")";
                //System.out.println(message);

                // Log packet (and queue state) for debugging
                if(SimulationLogger.hasPacketsTrackingEnabled()){
                    SimulationLogger.logPacket(message);
                }

                // Logging dropped packet
                SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED");
                if (p.getSourceId() == this.getOwnId()) {
                    SimulationLogger.increaseStatisticCounter("PACKETS_DROPPED_AT_SOURCE");
                }

                // Tracking drops per rank (above only tracks drops)
                if (SimulationLogger.hasDropsTrackingEnabled()) {
                    int rank = (int) p.getPriority();
                    SimulationLogger.logDropsPerRank(this.getOwnId(), rank, 1);
                }
            }
        }
    }
}
