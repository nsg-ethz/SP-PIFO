package ch.ethz.systems.netbench.xpt.ports.SPPIFO_WFQ;

import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;
import ch.ethz.systems.netbench.xpt.tcpbase.PriorityHeader;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

// Particular implementation of SPPIFO where ranks are specified following STFQ
public class WFQSPPIFOQueue implements Queue {

    private final ArrayList<ArrayBlockingQueue> queueList;
    private final Map queueBounds;
    private ReentrantLock reentrantLock;

    // STFQ Attributes
    private final Map last_finishTime;
    private int round;

    public WFQSPPIFOQueue(long numQueues, long perQueueCapacity){
        this.queueList = new ArrayList((int)numQueues);
        this.reentrantLock = new ReentrantLock();
        this.queueBounds = new HashMap();
        ArrayBlockingQueue fifo;
        for (int i=0; i<(int)numQueues; i++){
            fifo = new ArrayBlockingQueue<Packet>((int)perQueueCapacity);
            queueList.add(fifo);
            queueBounds.put(i, 0);
        }

        // STFQ Attributes
        this.last_finishTime = new HashMap();
        this.round = 0;
    }

    // Rank computation following STFQ as proposed in the PIFO paper
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

    public void setbackFinishTime(Packet p, int startTime){
        
        last_finishTime.put(p.getFlowId(), startTime);
    }

    // Round is the virtual start time of the last dequeued packet across all flows
    public void updateRound(Packet p){
        FullExtTcpPacket packet = (FullExtTcpPacket)p;
        this.round = (int)packet.getPriority();
    }

    // Packet dropped and null returned if selected queue exceeds its size
    @Override
    public boolean offer(Object o) {

        // Rank computation
        Packet packet = (Packet) o;
        int rank = this.computeRank((Packet)o);

        PriorityHeader header = (PriorityHeader) packet;
        header.setPriority((long)rank); // This makes no effect since each switch recomputes the ranks

        this.reentrantLock.lock();
        boolean returnValue = false;
        try {
            int currentQueueBound;
            for (int q=queueList.size()-1; q>=0; q--){
                currentQueueBound = (int)queueBounds.get(q);
                if ((currentQueueBound <= rank) || q==0) {
                    boolean result = queueList.get(q).offer(o);
                    if (!result){
                        // System.out.println("SPPIFO: Packet with rank " + rank + " has been dropped from queue " + q + ".");
                        returnValue = false;
                        last_finishTime.put(packet.getFlowId(), (int)last_finishTime.get(packet.getFlowId()) - ((int)packet.getSizeBit()/8));
                        // setbackFinishTime((Packet)o, rank);
                        break;
                    } else {
                        // Try to set finish time only for packets enqueued
                        // System.out.println("SPPIFO: Packet with rank " + rank + " enqueued in queue " + q + ".");
                        queueBounds.put(q, rank);
                        int cost = currentQueueBound - rank;
                        if (cost > 0){
                            // System.out.println("SPPIFO: Blocking occurred with cost = " + cost + ". Reacting to blocking...");
                            for (int w=queueList.size()-1; w>q; w--){
                                currentQueueBound = (int) queueBounds.get(w);
                                queueBounds.put(w, currentQueueBound-cost); // Update queue bounds
                            }
                        }
                        returnValue = true;
                        break;
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            this.reentrantLock.unlock();
            return returnValue;
        }
    }

    @Override
    public int size() {
        int size = 0;
        for (int q=0; q<queueList.size(); q++){
            size = size + queueList.get(q).size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        for (int q=0; q<queueList.size(); q++){
            if(!queueList.get(q).isEmpty()){
                empty = false;
            }
        }
        return empty;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object poll() {
        this.reentrantLock.lock();
        try {
            Packet p;
            for (int q=0; q<queueList.size(); q++){
                p = (Packet) queueList.get(q).poll();
                if (p != null){

                    // Update round number
                    this.updateRound(p);

                    return p;
                }
            }
            return null;
        } catch (Exception e){
            return null;
        } finally {
            this.reentrantLock.unlock();
        }
    }



    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }
}