package ch.ethz.systems.netbench.xpt.ports.SPPIFO;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;
import ch.ethz.systems.netbench.xpt.tcpbase.PriorityHeader;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class SPPIFOQueue implements Queue {

    private final ArrayList<ArrayBlockingQueue> queueList;
    private final Map queueBounds;
    private int ownId;
    private String stepSize;

    public SPPIFOQueue(long numQueues, long perQueueCapacity, NetworkDevice ownNetworkDevice, String stepSize){
        this.queueList = new ArrayList((int)numQueues);
        this.queueBounds = new HashMap();

        ArrayBlockingQueue fifo;
        for (int i=0; i<(int)numQueues; i++){
            fifo = new ArrayBlockingQueue<Packet>((int)perQueueCapacity);
            queueList.add(fifo);
            queueBounds.put(i, 0);
        }
        this.ownId = ownNetworkDevice.getIdentifier();
        this.stepSize = stepSize;
    }

    // Packet dropped and null returned if selected queue exceeds its size
    @Override
    public boolean offer(Object o) {

        // Extract rank from header
        Packet packet = (Packet) o;
        PriorityHeader header = (PriorityHeader) packet;
        int rank = (int)header.getPriority();

        boolean returnValue = false;

        // Mapping based on queue bounds
        int currentQueueBound;
        for (int q=queueList.size()-1; q>=0; q--){
            currentQueueBound = (int)queueBounds.get(q);
            if ((currentQueueBound <= rank) || q==0) {
                boolean result = queueList.get(q).offer(o);
                if (!result){
                    returnValue = false;
                    break;
                } else {

                    // Per-packet queue bound adaptation
                    queueBounds.put(q, rank);
                    int cost = currentQueueBound - rank;
                    if (cost > 0){
                        for (int w=queueList.size()-1; w>q; w--){
                            currentQueueBound = (int) queueBounds.get(w);

                            // Update queue bounds
                            if (this.stepSize.equals("cost")){
                                queueBounds.put(w, currentQueueBound-cost);
                            } else if (this.stepSize.equals("1")){
                                queueBounds.put(w, currentQueueBound-1);
                            } else if (this.stepSize.equals("rank")){
                                queueBounds.put(w, currentQueueBound-rank);
                            } else if (this.stepSize.equals("queueBound")){
                                queueBounds.put(w, queueBounds.get(w-1));
                            } else {
                                System.out.println("ERROR: SP-PIFO step size not supported.");
                            }
                        }
                    }
                    returnValue = true;
                    break;
                }
            }
        }
        return returnValue;
    }

    @Override
    public Object poll() {
        Packet p;
        for (int q=0; q<queueList.size(); q++){
            p = (Packet) queueList.get(q).poll();
            if (p != null){

                PriorityHeader header = (PriorityHeader) p;
                int rank = (int)header.getPriority();

                // Log rank of packet enqueued and queue selected if enabled
                if(SimulationLogger.hasRankMappingEnabled()){
                    SimulationLogger.logRankMapping(this.ownId, rank, q);
                }

                if(SimulationLogger.hasQueueBoundTrackingEnabled()){
                    for (int c=queueList.size()-1; c>=0; c--){
                        SimulationLogger.logQueueBound(this.ownId, c, (int)queueBounds.get(c));
                    }
                }

                // Check whether there is an inversion: a packet with smaller rank in queue than the one polled
                if (SimulationLogger.hasInversionsTrackingEnabled()) {
                    int count_inversions = 0;
                    for (int i = 0; i <= queueList.size() - 1; i++) {
                        Object[] currentQueue = queueList.get(i).toArray();
                        for (int j = 0; j < currentQueue.length; j++) {
                            int r = (int) ((FullExtTcpPacket) currentQueue[j]).getPriority();
                            if (r < rank) {
                                count_inversions++;
                            }
                        }
                    }
                    if (count_inversions != 0) {
                        SimulationLogger.logInversionsPerRank(this.ownId, rank, count_inversions);
                    }
                }
                return p;
            }
        }
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        for (int q=0; q<queueList.size(); q++){
            size += queueList.get(q).size();
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
