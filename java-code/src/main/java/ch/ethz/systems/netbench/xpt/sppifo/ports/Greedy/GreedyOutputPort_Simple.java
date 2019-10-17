package ch.ethz.systems.netbench.xpt.sppifo.ports.Greedy;

import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.network.Packet;


public class GreedyOutputPort_Simple extends OutputPort {

    public GreedyOutputPort_Simple(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long numberQueues, long sizePerQueuePackets, long adaptationPeriod, long maxRank) {
        super(ownNetworkDevice, targetNetworkDevice, link, new GreedyQueue(numberQueues, sizePerQueuePackets, ownNetworkDevice, adaptationPeriod, maxRank));
    }

    /**
     * Enqueue the given packet.
     * Drops it if the queue is full (tail drop).
     *
     * @param packet    Packet instance
     */
    @Override
    public void enqueue(Packet packet) {

        // Enqueue packet
        potentialEnqueue(packet);
    }

}
