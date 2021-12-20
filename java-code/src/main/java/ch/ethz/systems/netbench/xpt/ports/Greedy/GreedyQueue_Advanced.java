package ch.ethz.systems.netbench.xpt.ports.Greedy;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.Packet;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;
import ch.ethz.systems.netbench.xpt.tcpbase.PriorityHeader;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class GreedyQueue_Advanced implements Queue {

    private ReentrantLock reentrantLock;
    private int ownId;
    private final ArrayList<ArrayBlockingQueue> queueList;
    private final Map queueBounds;
    private Map queueBoundsMinus;
    private Map queueBoundsPlus;
    private int generalPacketCounter;
    private Map countersA11;
    private Map countersA12;
    private Map countersA21;
    private Map countersA22;
    private Map countersB11;
    private Map countersB12;
    private Map countersB21;
    private Map countersB22;
    private int rank_bound;
    private long unpifoness;
    private Boolean fixQueueBounds;

    public GreedyQueue_Advanced(long numQueues, long perQueueCapacity, NetworkDevice ownNetworkDevice, String initialization, String fixQueueBounds){

        // General
        this.reentrantLock = new ReentrantLock();
        this.ownId = ownNetworkDevice.getIdentifier();
        this.unpifoness = 0;

        // The actual queues where packets are stored
        this.queueList = new ArrayList((int)numQueues);

        // Initialization of those queues
        ArrayBlockingQueue fifo;
        for (int i=0; i<(int)numQueues; i++){
            fifo = new ArrayBlockingQueue<Packet>((int)perQueueCapacity);
            queueList.add(fifo);
        }

        // The queue bounds for the mapping process
        this.queueBounds = new HashMap();

        // Initialization of queue bounds
        for (int i=0; i<(int)numQueues; i++){
            queueBounds.put(i, i);
        }
        if (fixQueueBounds.equals("true")){
            this.fixQueueBounds = true;
        } else {
            this.fixQueueBounds = false;
        }

        // The counters for the aggregated algorithm
        this.generalPacketCounter = 0;
        this.countersA11 = new HashMap();
        this.countersA12 = new HashMap();
        this.countersA21 = new HashMap();
        this.countersA22 = new HashMap();
        this.countersB11 = new HashMap();
        this.countersB12 = new HashMap();
        this.countersB21 = new HashMap();
        this.countersB22 = new HashMap();

        // Initialization of counters
        for (int i=0; i<(int)numQueues; i++) {
            countersA11.put(i, 0);
            countersA12.put(i, 0);
            countersA21.put(i, 0);
            countersA22.put(i, 0);
            countersB11.put(i, 0);
            countersB12.put(i, 0);
            countersB21.put(i, 0);
            countersB22.put(i, 0);
        }

        // Rank-monitoring parameters to support rank distributions where ranks are non-contiguous
        this.queueBoundsMinus = new HashMap();
        this.queueBoundsPlus = new HashMap();

        // Initialization of rank-monitoring parameters
        for (int i=0; i<(int)numQueues; i++) {
            queueBoundsPlus.put(i, 0);
            queueBoundsMinus.put(i, 0);
        }

        // Configurable program parameters
        if (initialization.equals("default")) {
            this.rank_bound = 100; // Needs to be bigger than the initial maximum queue bound
            queueBounds.put(0, 1);
            queueBounds.put(1, 2);
            queueBounds.put(2, 3);
            queueBounds.put(3, 4);
            queueBounds.put(4, 5);
            queueBounds.put(5, 6);
            queueBounds.put(6, 7);
            queueBounds.put(7, 8);

        } else if (initialization.equals("uniform")) {
            this.rank_bound = 100; // Needs to be bigger than the initial maximum queue bound
            queueBounds.put(0, 0);
            queueBounds.put(1, 12);
            queueBounds.put(2, 25);
            queueBounds.put(3, 37);
            queueBounds.put(4, 50);
            queueBounds.put(5, 62);
            queueBounds.put(6, 75);
            queueBounds.put(7, 87);

        } else if (initialization.equals("uniform32")) {
            this.rank_bound = 100; // Needs to be bigger than the initial maximum queue bound
            for (int i=0; i<numQueues; i++){
                queueBounds.put(i, i*3);
            }

        } else {
            System.out.println("Greedy Warning: Initialization strategy not supported.");
        }

        for (int q=0; q<queueList.size(); q++){
            if (q==queueList.size()-1){
                this.queueBoundsPlus.put(q,this.rank_bound);
                this.queueBoundsMinus.put(q,this.queueBounds.get(q-1));
            } else if (q==0){
                this.queueBoundsPlus.put(q,this.queueBounds.get(q+1));
                this.queueBoundsMinus.put(q,0);
            } else{
                this.queueBoundsPlus.put(q,this.queueBounds.get(q+1));
                this.queueBoundsMinus.put(q,this.queueBounds.get(q-1));
            }
        }
    }

    public int importance(int rank){
        return 1;
    }

    // Packet dropped and null returned if selected queue exceeds its size
    @Override
    public boolean offer(Object o) {

        // Rank is extracted from the header
        Packet packet = (Packet) o;
        PriorityHeader header = (PriorityHeader) packet;
        int rank = (int)header.getPriority();

        this.reentrantLock.lock();
        boolean returnValue = false;
        try {

            // Keep track of the current queue bounds (Figure 13a in the SP-PIFO paper)
            for (int q=queueList.size()-1; q>=0; q--){
                if(SimulationLogger.hasQueueBoundTrackingEnabled()){
                   SimulationLogger.logQueueBound(this.ownId, q, (int)queueBounds.get(q));
               }
            }

            // Aggregate adaptation parameters
            int currentQueueBound, currentQueueBoundPlus, currentQueueBoundMinus;
            int QLm, QLh, QLmplus, QLmminus, sumA1, sumA2, sumB1, sumB2;

            // SP-PIFO queue scanning process
            for (int q=queueList.size()-1; q>=0; q--){
                currentQueueBound = (int)queueBounds.get(q);
                if ((currentQueueBound <= rank) || q==0) {
                    boolean result = queueList.get(q).offer(o);

                    if (!result){
                        // System.out.println("Greedy: Packet with rank " + rank + " has been dropped from queue " + q + ".");
                        returnValue = false;
                        break;
                    } else {

                        // Update general counter
                        this.generalPacketCounter = this.generalPacketCounter + 1;

                        // We compute the perceived rank
                        Object[] contentPIFO = queueList.get(q).toArray();
                        Arrays.sort(contentPIFO);
                        Packet packet_maxrank = (Packet) contentPIFO[0];
                        PriorityHeader header_maxrank = (PriorityHeader) packet_maxrank;
                        int rank_perceived = (int)header_maxrank.getPriority();
                        if (rank_perceived > rank){
                            this.unpifoness = this.unpifoness + (rank_perceived - rank);
                        }

                        // IMPORTANT: If packet enqueued here, means that QLm <= rank < QLh
                        // Packet enqueued, we update counters, and return true
                        if (q==queueList.size()-1){
                            QLh = rank_bound;
                            QLm = (int)this.queueBounds.get(q);
                        } else {
                            QLh = (int)this.queueBounds.get(q+1);
                            QLm = (int)this.queueBounds.get(q);
                        }

                        // To consider non continuous rank distributions, we have to keep track of the Qm+1 and Qh-1 ranks per each queue
                        // This way we are sure that the boundary move we are analyzing will not be an empty rank
                        currentQueueBoundPlus = (int)this.queueBoundsPlus.get(q);
                        if (rank > QLm && rank < currentQueueBoundPlus){
                            this.queueBoundsPlus.put(q, rank);
                        }

                        // Update counters per queue
                        Object currentPackets = countersB21.get(q);
                        if (currentPackets == null){
                            countersB21.put(q, rank);
                        } else {
                            countersB21.put(q, (int)currentPackets+rank);
                        }

                        currentPackets = countersB22.get(q);
                        if (currentPackets == null){
                            countersB22.put(q, 1);
                        } else {
                            countersB22.put(q, (int)currentPackets+1);
                        }

                        if (rank != QLm){
                            currentPackets = countersA21.get(q);
                            if (currentPackets == null){
                                countersA21.put(q, rank);
                            } else {
                                countersA21.put(q, (int)currentPackets+rank);
                            }

                            currentPackets = countersA22.get(q);
                            if (currentPackets == null){
                                countersA22.put(q, 1);
                            } else {
                                countersA22.put(q, (int)currentPackets+1);
                            }
                        }

                        if (q!=queueList.size()-1){

                            currentQueueBoundMinus = (int) this.queueBoundsMinus.get(q + 1);
                            if (rank > currentQueueBoundMinus) {
                                this.queueBoundsMinus.put(q + 1, rank);
                            }

                            currentPackets = countersA11.get(q+1);
                            if (currentPackets == null){
                                countersA11.put(q+1, importance(rank));
                            } else {
                                countersA11.put(q+1, (int)currentPackets+importance(rank));
                            }

                            currentPackets = countersA12.get(q+1);
                            if (currentPackets == null){
                                countersA12.put(q+1, (importance(rank))*rank);
                            } else {
                                countersA12.put(q+1, (int)currentPackets+(importance(rank))*rank);
                            }

                            if(rank != (int)this.queueBoundsMinus.get(q+1)){
                                currentPackets = countersB11.get(q+1);
                                if (currentPackets == null){
                                    countersB11.put(q+1, importance(rank));
                                } else {
                                    countersB11.put(q+1, (int)currentPackets+importance(rank));
                                }

                                currentPackets = countersB12.get(q+1);
                                if (currentPackets == null){
                                    countersB12.put(q+1, (importance(rank))*rank);
                                } else {
                                    countersB12.put(q+1, (int)currentPackets+(importance(rank))*rank);
                                }
                            }
                        }

                        returnValue = true;
                        break;
                    }
                }
            }

            // When the threshold is reached, aggregate adaptation is considered based on the values in counters
            if(this.generalPacketCounter == 1000) {

                // Each queue bound expected unpifoness is compared to the one achieved by moving the boundaries
                for(int i=queueBounds.size()-1; i>0; i--){

                    // Obtain the values of adjacent ranks to analyze
                    QLm = (int)this.queueBounds.get(i);
                    QLmplus = (int)this.queueBoundsPlus.get(i);
                    QLmminus = (int)this.queueBoundsMinus.get(i);

                    sumA1 = ((QLm) * (int)countersA11.get(i)) - (int)countersA12.get(i);
                    sumA2 = (importance(QLm) * (int)countersA21.get(i)) - (importance(QLm) * (QLm) * (int)countersA22.get(i));

                    if (i==(queueBounds.size()-1)){
                        if ((sumA1 < sumA2) && (QLmplus != (int)this.rank_bound)){
                            if (!this.fixQueueBounds){
                                this.queueBounds.put(i, QLmplus);
                            }
                        } else {

                            sumB1 = ((QLmminus) * (int)countersB11.get(i)) - (int)countersB12.get(i);
                            sumB2 = (importance(QLmminus) * (int)countersB21.get(i)) - (importance(QLmminus) * (QLmminus) * (int)countersB22.get(i));

                            if ((sumB1 > sumB2) && (QLmminus != (int)this.queueBounds.get(i-1))){
                                if (!this.fixQueueBounds){
                                    this.queueBounds.put(i, QLmminus);
                                }
                            }
                        }
                    } else {
                        if ((sumA1 < sumA2) && (QLmplus != (int)this.queueBounds.get(i+1))){
                            if (!this.fixQueueBounds){
                                this.queueBounds.put(i, QLmplus);
                            }
                        } else {

                            sumB1 = ((QLmminus) * (int)countersB11.get(i)) - (int)countersB12.get(i);
                            sumB2 = (importance(QLmminus) * (int)countersB21.get(i)) - (importance(QLmminus) * (QLmminus) * (int)countersB22.get(i));

                            if ((sumB1 > sumB2) && (QLmminus != (int)this.queueBounds.get(i-1))){
                                if (!this.fixQueueBounds){
                                    this.queueBounds.put(i, QLmminus);
                                }
                            }
                        }
                    }

                }

                // Reset the counters for the next iteration
                this.queueBoundsPlus.clear();
                this.queueBoundsMinus.clear();
                this.generalPacketCounter = 0;
                this.countersA11.clear();
                this.countersA12.clear();
                this.countersA21.clear();
                this.countersA22.clear();
                this.countersB11.clear();
                this.countersB12.clear();
                this.countersB21.clear();
                this.countersB22.clear();
                for (int i=0; i<queueList.size(); i++){
                    countersA11.put(i,0);
                    countersA12.put(i,0);
                    countersA21.put(i,0);
                    countersA22.put(i,0);
                    countersB11.put(i,0);
                    countersB12.put(i,0);
                    countersB21.put(i,0);
                    countersB22.put(i,0);
                }

                // Reinitialize the adjacent rank values based on the updated queue bounds
                for (int q=0; q<queueList.size(); q++){
                    if (q==queueList.size()-1){
                        this.queueBoundsPlus.put(q,this.rank_bound);
                        this.queueBoundsMinus.put(q,this.queueBounds.get(q-1));
                    } else if (q==0){
                        this.queueBoundsPlus.put(q,this.queueBounds.get(q+1));
                        this.queueBoundsMinus.put(q,0);
                    } else{
                        this.queueBoundsPlus.put(q,this.queueBounds.get(q+1));
                        this.queueBoundsMinus.put(q,this.queueBounds.get(q-1));
                    }
                }
            }

        } catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
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
                    int rank = (int)header.getPriority();
                    // System.out.println("SPPIFO: Dequeued packet with rank" + rank + ", from queue " + q + ". Queue size: " + queueList.get(q).size());

                    // Log rank of packet enqueued and queue selected if enabled
                    if(SimulationLogger.hasRankMappingEnabled()){
                        SimulationLogger.logRankMapping(this.ownId, rank, q);
                    }

                    // Check whether there is an inversion: a packet with smaller rank in queue than the one polled
                    if (SimulationLogger.hasInversionsTrackingEnabled()) {
                        int rankSmallest = 1000;
                        for (int i = 0; i <= queueList.size() - 1; i++) {
                            Object[] currentQueue = queueList.get(i).toArray();
                            if (currentQueue.length > 0) {
                                Arrays.sort(currentQueue);
                                FullExtTcpPacket currentMin = (FullExtTcpPacket) currentQueue[0];
                                if ((int)currentMin.getPriority() < rankSmallest){
                                    rankSmallest = (int) currentMin.getPriority();
                                }
                            }
                        }

                        if (rankSmallest < rank) {
                            SimulationLogger.logInversionsPerRank(this.ownId, rank, 1);
                            // System.out.println("Rank " + rank + " is blocking the transmission to " + rankSmallest);
                        }
                    }
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
