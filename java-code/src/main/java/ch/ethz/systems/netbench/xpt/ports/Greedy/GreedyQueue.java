package ch.ethz.systems.netbench.xpt.ports.Greedy;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.PriorityHeader;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;


public class GreedyQueue implements Queue {

    private final ArrayList<ArrayBlockingQueue> queueList;
    private final Map queueBounds;
    private ReentrantLock reentrantLock;
    private int ownId;
    private int generalPacketCounter;
    private Map packetsPerRank;
    private long rankBound;
    private long adaptationPeriod;


    public GreedyQueue(long numQueues, long perQueueCapacity, NetworkDevice ownNetworkDevice, long adaptationPeriod, long maxRank){
        this.queueList = new ArrayList((int)numQueues);
        this.reentrantLock = new ReentrantLock();
        this.queueBounds = new HashMap();

        ArrayBlockingQueue fifo;
        for (int i=0; i<(int)numQueues; i++){
            fifo = new ArrayBlockingQueue<Packet>((int)perQueueCapacity);
            queueList.add(fifo);
            queueBounds.put(i, i);
        }

        this.ownId = ownNetworkDevice.getIdentifier();
        this.generalPacketCounter = 0;
        this.packetsPerRank = new HashMap();
        this.rankBound = maxRank;
        this.adaptationPeriod = adaptationPeriod;
    }

    // Packet dropped and null returned if selected queue exceeds its size
    @Override
    public boolean offer(Object o) {

        Packet packet = (Packet) o;
        PriorityHeader header = (PriorityHeader) packet;
        int rank = (int)header.getPriority();

        // This in for the case that the maxRank is not known in advance
        //if (rank > this.rankBound){
        //    this.rankBound = rank + 1; //it is the bound, so nominal max rank+1
        //}

        this.reentrantLock.lock();
        boolean returnValue = false;
        try {

            this.generalPacketCounter = this.generalPacketCounter + 1;
            Object currentPackets = packetsPerRank.get(rank);

            if (currentPackets == null){
                packetsPerRank.put(rank, 1);
            } else {
                packetsPerRank.put(rank, (int)currentPackets+1);
            }

            int QLh, QLm, QLl;
            if(this.generalPacketCounter == this.adaptationPeriod) {

                int unpifoness_total = 0;
                int unpifoness_queue;

                // We compute the current unpifoness
                for(int i=queueList.size()-1; i>=0; i--) {

                    // We initialize the unpifoness counter
                    unpifoness_queue = 0;

                    // We set the queue limits for that queue
                    if (i == queueList.size() - 1) {
                        QLh = (int)rankBound;
                        QLm = (int) this.queueBounds.get(i);
                    } else {
                        QLh = (int) this.queueBounds.get(i + 1);
                        QLm = (int) this.queueBounds.get(i);
                    }

                    // We compute the unpifoness for the queue
                    Object rate_r;
                    Object rate_rprime;
                    for (int r = QLm; r < QLh; r++) {
                        for (int rprime = r+1; rprime < QLh; rprime++) {
                            rate_r = packetsPerRank.get(r);
                            rate_rprime = packetsPerRank.get(rprime);
                            if (rate_r != null && rate_rprime != null) {
                                unpifoness_queue = unpifoness_queue + (((int) rate_r * (int) rate_rprime) * ((int)rankBound - r) * (rprime - r))/((int)adaptationPeriod^2);
                            }
                        }
                    }

                    // We add it to the total counter
                    unpifoness_total = unpifoness_total + unpifoness_queue;
                }

                // We log the resulting unpifoness for the iteration
                if(SimulationLogger.hasUnpifonessTrackingEnabled()){
                    SimulationLogger.logUnpifoness(this.ownId, unpifoness_total);
                }

                // Every adaptation period, we consider reorganizing queue revels
                // In queue 7 we have packets from rank (MAX-RANK to QL7]
                // In queue 6 we have packets from rank (QL7 to QL6]
                // ...
                // In queue 0 we have all packets from rank (0 to QL1]
                // QL0 is useless, so we don't need to modify

                for(int i=queueList.size()-1; i>0; i--){

                    if (i==queueList.size()-1){
                        QLh = (int)rankBound;
                        QLm = (int)this.queueBounds.get(i);
                        QLl = (int)this.queueBounds.get(i-1);
                    } else {
                        QLh = (int)this.queueBounds.get(i+1);
                        QLm = (int)this.queueBounds.get(i);
                        QLl = (int)this.queueBounds.get(i-1);
                    }

                    int sumA1 = 0;
                    int sumA2 = 0;
                    int sumB1 = 0;
                    int sumB2 = 0;
                    Object rate1;
                    Object rate2;

                    // Do we want to switch QLm to QLm+1?
                    // This would make rank QLm to be moved from lower to higher priority queue
                    // Unpifoness generated by moving QLm to higher priority queue
                    for (int r=QLl; r<QLm; r++){
                        rate1 = packetsPerRank.get(r);
                        rate2 = packetsPerRank.get(QLm);
                        if(rate1 != null && rate2 != null){
                            sumA1 = sumA1 + (((int)rate1*(int)rate2)*(QLm-r));
                        }
                    }

                    // Unpifoness reduction by removing QLm from the lower
                    for (int rprime=QLm+1; rprime<QLh; rprime++){
                        rate1 = packetsPerRank.get(rprime);
                        rate2 = packetsPerRank.get(QLm);
                        if(rate1 != null && rate2 != null){
                            sumA2 = sumA2 + (((int)rate1*(int)rate2)*(rprime-QLm));
                        }
                    }

                    if (sumA1 < sumA2){
                        this.queueBounds.put(i, QLm+1);
                    } else {

                        // Do we want to switch QLm to QLm-1?
                        // Unpifoness reduction by removing QLm-1 from the higher priority queue
                        for (int r=QLl; r<QLm-1; r++){
                            rate1 = packetsPerRank.get(r);
                            rate2 = packetsPerRank.get(QLm-1);
                            if(rate1 != null && rate2 != null){
                                sumB1 = sumB1 + (((int)rate1*(int)rate2)*((QLm-1)-r));
                            }
                        }

                        // Unpifoness generated by bringing QLm-1 to the lower priority queue
                        for (int rprime=QLm; rprime<QLh; rprime++){
                            rate1 = packetsPerRank.get(rprime);
                            rate2 = packetsPerRank.get(QLm-1);
                            if(rate1 != null && rate2 != null){
                                sumB2 = sumB2 + (((int)rate1*(int)rate2)*(rprime-(QLm-1)));
                            }
                        }

                        if (sumB1 > sumB2){
                            this.queueBounds.put(i, QLm-1);
                        }
                    }
                }
                this.generalPacketCounter = 0;
                this.packetsPerRank.clear();
            }

            int currentQueueBound;
            for (int q=queueList.size()-1; q>=0; q--){
                currentQueueBound = (int)queueBounds.get(q);
                if ((currentQueueBound <= rank) || q==0) {
                    boolean result = queueList.get(q).offer(o);
                    if (!result){
                        // System.out.println("Greedy: Packet with rank " + rank + " has been dropped from queue " + q + ".");
                        returnValue = false;
                        break;
                    } else {
                        // System.out.println("Greedy: Packet with rank " + rank + " enqueued in queue " + q + ".");
                        returnValue = true;
                        break;
                    }
                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            this.reentrantLock.unlock();
            // System.out.println("Packet with rank " + rank + "enqueued_flag" + returnValue);
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
                    PriorityHeader header = (PriorityHeader) p;
                    // int rank = (int)header.getPriority();
                    // System.out.println("Greedy: Dequeued packet with rank" + rank + ", from queue " + q + ". Queue size: " + queueList.get(q).size());
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