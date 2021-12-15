package ch.ethz.systems.netbench.xpt.ports.PIFO_WFQ;

import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;
import ch.ethz.systems.netbench.xpt.tcpbase.PriorityHeader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class WFQPIFOQueue extends PriorityBlockingQueue implements Queue {

    private final int maxItems;
    private Lock reentrantLock;
    private int ownId;
    private int targetId;

    /*STFQ Attributes*/
    private final Map last_finishTime;
    private int round;

    public WFQPIFOQueue(long maxItems, int targetId, int ownId){
        this.ownId = ownId;
        this.targetId = targetId;

        this.maxItems = (int)maxItems;
        this.reentrantLock = new ReentrantLock();

        /*STFQ Attributes*/
        this.last_finishTime = new HashMap();
        this.round = 0;
    }

    /*Rank computation following STFQ as proposed in the PIFO paper*/
    public int computeRank(Packet p){
        int startTime = this.round;
        if(last_finishTime.containsKey(p.getFlowId())){
            if((int) last_finishTime.get(p.getFlowId()) > round){
                startTime = (int)last_finishTime.get(p.getFlowId());
            }
        }
        
        int flowWeight = 8;
        int finishingTime_update = startTime + ((int)p.getSizeBit()/flowWeight);
        last_finishTime.put(p.getFlowId(), finishingTime_update);
        return startTime;
    }

    /*Round is the virtual start time of the last dequeued packet across all flows*/
    public void updateRound(Packet p){
        PriorityHeader header = (PriorityHeader) p;
        int rank = (int)header.getPriority();
        this.round = rank;
    }

    public Packet offerPacket(Object o, int ownID) {

        this.reentrantLock.lock();

        /*Rank computation*/
        FullExtTcpPacket packet = (FullExtTcpPacket) o;
        int rank = this.computeRank(packet);

        PriorityHeader header = (PriorityHeader) packet;
        header.setPriority((long)rank); // This makes no effect since each switch recomputes the ranks

        boolean success = true;
        try {
            /* As the original PBQ is has no limited size, the packet is always inserted */
            success = super.offer(packet); /* This method will always return true */

            /* We control the size by removing the extra packet */
            if (this.size()>maxItems-1){
                Object[] contentPIFO = this.toArray();
                Arrays.sort(contentPIFO);
                packet = (FullExtTcpPacket) contentPIFO[this.size()-1];
                last_finishTime.put(packet.getFlowId(), (int)last_finishTime.get(packet.getFlowId()) - ((int)packet.getSizeBit()/8));
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

            // Update round number
            this.updateRound(packet);
            return packet;
        } catch (Exception e){
            return null;
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
