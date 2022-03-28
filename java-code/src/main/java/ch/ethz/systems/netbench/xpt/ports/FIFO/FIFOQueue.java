package ch.ethz.systems.netbench.xpt.ports.FIFO;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;

import java.util.concurrent.LinkedBlockingQueue;

public class FIFOQueue<E> extends LinkedBlockingQueue<E> {

    private final int ownId;                            // Own network device identifier


    public FIFOQueue(int ownId) {
        super();
        this.ownId = ownId;
    }

    @Override
    public E poll() {
        E element = super.poll();
        Packet p = (Packet) element;

        // We just create the class to be able to measure inversions at dequeue for the FIFO queue
        if (p != null){
            int rank = (int)((FullExtTcpPacket)p).getPriority();


            // Check whether there is an inversion: a packet with smaller rank in queue than the one polled
            if (SimulationLogger.hasInversionsTrackingEnabled()) {
                int count_inversions = 0;
                Object[] contentFIFO = this.toArray();
                for (int j = 0; j < contentFIFO.length; j++) {
                    Packet cq = (Packet) contentFIFO[j];
                    int r = (int) ((FullExtTcpPacket) cq).getPriority();
                    if (r < rank) {
                        count_inversions++;
                    }
                }
                if (count_inversions != 0) {
                    SimulationLogger.logInversionsPerRank(this.ownId, rank, count_inversions);
                }
            }
        }
        return element;
    }
}
