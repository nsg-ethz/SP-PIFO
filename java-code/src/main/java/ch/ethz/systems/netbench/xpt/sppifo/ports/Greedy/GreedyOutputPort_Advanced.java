package ch.ethz.systems.netbench.xpt.sppifo.ports.Greedy;

import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.network.Packet;


public class GreedyOutputPort_Advanced extends OutputPort {

    public GreedyOutputPort_Advanced(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long numberQueues, long sizePerQueuePackets, String initialization, String fixQueueBounds) {
        super(ownNetworkDevice, targetNetworkDevice, link, new GreedyQueue_Advanced(numberQueues, sizePerQueuePackets, ownNetworkDevice, initialization, fixQueueBounds));
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
