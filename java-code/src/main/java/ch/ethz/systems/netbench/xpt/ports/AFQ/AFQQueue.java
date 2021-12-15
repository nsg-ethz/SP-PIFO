package ch.ethz.systems.netbench.xpt.ports.AFQ;

import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class AFQQueue implements Queue {

    private final ArrayList<ArrayBlockingQueue> queueList;
    private final Map queuePriorities;
    private final Map flowBids;
    private long bytesPerRound;
    private long currentRound;
    private long servingQueue;
    private ReentrantLock reentrantLock;
    private int ownId;

    public AFQQueue(long numQueues, long perQueueCapacity, long bytesPerRound, int ownId){
        perQueueCapacity = 320;
        // bytesPerRound = 2000;
        this.queueList = new ArrayList((int)numQueues);
        this.queuePriorities = new HashMap();
        ArrayBlockingQueue fifo;
        for (int i=0; i<(int)numQueues; i++){
            fifo = new ArrayBlockingQueue((int)perQueueCapacity);
            queueList.add(fifo);
            queuePriorities.put(i, i);
        }

        this.flowBids = new HashMap();
        this.bytesPerRound = bytesPerRound;
        this.currentRound = 0;
        this.servingQueue = 0;
        this.reentrantLock = new ReentrantLock();
        this.ownId = ownId;
    }

    @Override
    public boolean offer(Object o){

        this.reentrantLock.lock();
        FullExtTcpPacket p = (FullExtTcpPacket) o;
        boolean result = true;

        try {

            // Compute the packet bid (when will the last byte be transmitted) as the max. between the current round (in bytes) and the last bid of the flow
            long bid = this.currentRound * this.bytesPerRound;

            if(flowBids.containsKey(p.getFlowId())){
                if(bid < (Long)flowBids.get(p.getFlowId())){
                    bid = (Long)flowBids.get(p.getFlowId());
                }
            }
            bid = bid + (p.getSizeBit()/8);

            long packetRound = bid/this.bytesPerRound;

            if((packetRound - this.currentRound) > queueList.size()){
                result = false; // Packet dropped since computed round is too far away
            } else {
                result = queueList.get((int)packetRound%(queueList.size())).offer(p);
                if (!result){
                } else {
                    flowBids.put(p.getFlowId(), bid);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Probably the bid size has been exceeded, transmit less packets ");
            System.out.println("Exception AFQ offer: " + e.getMessage() + e.getLocalizedMessage());
        } finally {
            this.reentrantLock.unlock();
            return result;
        }
    }

    @Override
    public Packet poll(){
        this.reentrantLock.lock();
        try {
            Packet p = null;
            while (p == null){
                if (this.size() != 0) {
                    if (!queueList.get((int) this.servingQueue).isEmpty()) {
                        p = (Packet) queueList.get((int) this.servingQueue).poll();
                        return p;
                    } else {
                        this.servingQueue = (this.servingQueue + 1) % this.queueList.size();
                        this.currentRound++;
                    }
                }
            }
            return null;
        }
        finally {
            this.reentrantLock.unlock();
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
    public void clear() { }

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
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }
}
