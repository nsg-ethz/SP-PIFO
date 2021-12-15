package ch.ethz.systems.netbench.xpt.ports.PIFO;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;


public class PIFOQueue extends PriorityBlockingQueue implements Queue {

    private final int maxItems;
    private int ownId;

    public PIFOQueue(long maxItems, NetworkDevice ownNetworkDevice){
        this.maxItems = (int)maxItems;
        this.ownId = ownNetworkDevice.getIdentifier();
    }

    /* We put a limit to the PIFO queue so that if an inserted packet exceeds the capacity,
    the higher rank packet of the PIFO is dropped. */
    public Packet offerPacket(Object o) {

        FullExtTcpPacket packet = (FullExtTcpPacket) o;
        boolean success = true;

        // We tag the enqueue time to the packet, before offering it to PIFO
        // Instead of using the actual time, we use a per-packet counter
        long enqueueTime = Simulator.getEnqueueCounter();
        packet.setEnqueueTime(enqueueTime);
        Simulator.setEnqueueCounter(enqueueTime + 1);

        // If there is enough space, we just enqueue the packet and that's all
        if (this.size() <= maxItems-1) {

            success = this.offer(packet); // This method will always return true
            return null;

        // If the size exceeds the PIFO size, we drop (and return) the packet with highest rank that was enqueued last
        } else {

            success = super.offer(packet); // As the original PBQ is has no limited size, the packet is always inserted. This method will always return true
            Object[] contentPIFO = this.toArray();

            /* Debug */
            String message = "(Extended +1) PIFO Queue: [";
            Object[] defaultcontentPIFO = this.toArray();
            for (int j = 0; j<defaultcontentPIFO.length; j++){
                message = message + ((FullExtTcpPacket)defaultcontentPIFO[j]).getPriority() + "(" + ((FullExtTcpPacket)defaultcontentPIFO[j]).getEnqueueTime() + ") , ";
            }

            Arrays.sort(contentPIFO);
            FullExtTcpPacket drop_packet = (FullExtTcpPacket) contentPIFO[this.size()-1];
            this.remove(drop_packet);

            /* Debug */
            message = message + "]\n";
            message = message + "Packet dropped: " + drop_packet.getPriority() + "(" + drop_packet.getEnqueueTime() + ")";
            //System.out.println(message);

            return drop_packet;
        }
    }

    @Override
    public Object poll() {
        Packet packet = (Packet) super.poll();
        return packet;
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

}
