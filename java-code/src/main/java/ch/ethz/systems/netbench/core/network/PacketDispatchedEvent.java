package ch.ethz.systems.netbench.core.network;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;

/**
 * Event for the dispatch of a packet, i.e. when all of the bits
 * of the packet have been written to the link.
 */
public class PacketDispatchedEvent extends Event {

    private final OutputPort dispatchPort;
    private final Packet packet;

    /**
     * Packet dispatched event constructor.
     *
     * @param timeFromNowNs     Time in simulation nanoseconds from now
     * @param packet            Packet instance which is dispatched
     * @param dispatchPort      Port that has finished writing the packet to the link
     */
    PacketDispatchedEvent(long timeFromNowNs, Packet packet, OutputPort dispatchPort) {
        super(timeFromNowNs);
        this.packet = packet;
        this.dispatchPort = dispatchPort;

        // Log rank of packet enqueued and queue selected if enabled
        //if(SimulationLogger.hasRankMappingEnabled()){
        //    FullExtTcpPacket p = (FullExtTcpPacket) packet;
        //    int rank = (int)p.getPriority();
        //    SimulationLogger.logRankMapping(dispatchPort.getOwnId(), rank, 0);
        //}

    }

    @Override
    public void trigger() {
        dispatchPort.dispatch(packet);
    }

    @Override
    public String toString() {
        return "PacketDispatchedEvent<" + dispatchPort.getOwnId() + " -> " + dispatchPort.getTargetId() + ", " + this.getTime() + ", " + this.packet + ">";
    }

}
