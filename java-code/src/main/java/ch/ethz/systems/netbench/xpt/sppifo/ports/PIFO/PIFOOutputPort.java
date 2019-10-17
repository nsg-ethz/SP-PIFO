package ch.ethz.systems.netbench.xpt.sppifo.ports.PIFO;

import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.network.Packet;


public class PIFOOutputPort extends OutputPort {


    public PIFOOutputPort(NetworkDevice ownNetworkDevice, NetworkDevice targetNetworkDevice, Link link, long sizePackets) {
        super(ownNetworkDevice, targetNetworkDevice, link, new PIFOQueue(sizePackets));
    }

    /**
     * Enqueue the given packet.
     *
     * @param packet    Packet instance
     */
    @Override
    public void enqueue(Packet packet) {

        //Enqueue packet
        push(packet);
    }
}
