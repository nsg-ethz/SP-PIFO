package ch.ethz.systems.netbench.xpt.sppifo.ports.PIFO;

import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;


public class PIFOQueue extends PriorityBlockingQueue implements Queue {

    private final int maxItems;
    private Lock reentrantLock;


    public PIFOQueue(long maxItems){
        this.maxItems = (int)maxItems;
        this.reentrantLock = new ReentrantLock();
    }

    // We put a limit to the PIFO queue so that if an inserted packet exceeds the capacity,
    // the higher rank packet of the PIFO is dropped.

    public Packet offerPacket(Object o) {

        FullExtTcpPacket packet = (FullExtTcpPacket) o;

        this.reentrantLock.lock();
        boolean success = true;

        try {
            // As the original PBQ is has no limited size, the packet is always inserted
            success = super.offer(packet); // This method will always return true

            // If the size exceeds the PIFO size, we drop the packet with lowest priority (highest rank)
            if (this.size()>maxItems-1){
                Object[] contentPIFO = this.toArray();
                Arrays.sort(contentPIFO);
                packet = (FullExtTcpPacket) contentPIFO[this.size()-1];
                this.remove(packet);
                return packet;
            }

            return null;
        } finally {
            this.reentrantLock.unlock();
        }
    }

    @Override
    public Object poll() {
        this.reentrantLock.lock();
        try {
            Packet packet = (Packet) super.poll(); // As the super queue is unbounded, this method will always return true
            return packet;

        } finally {
            this.reentrantLock.unlock();
        }
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
