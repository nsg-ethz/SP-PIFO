package ch.ethz.systems.netbench.ext.basic;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.run.infrastructure.LinkGenerator;

/*Class by Albert Gran to describe different bandwidth links in Leaf-Spine Topology*/

/* Access links are of 10Gbps and leaf-spine links are 40Gbps. Remember that the difference between servers and switches
* is that switches don't have transport layer.*/

public class SplitBandwidthLinkGenerator extends LinkGenerator {

    private final long delayNs;
    private final double bandwidthBitPerNs;

    public SplitBandwidthLinkGenerator(long delayNs, double bandwidthBitPerNs) {
        this.delayNs = delayNs;
        this.bandwidthBitPerNs = bandwidthBitPerNs;
        SimulationLogger.logInfo("Link", "PERFECT_SIMPLE_LINK(delayNs=" + delayNs + ", bandwidthBitPerNs=" + bandwidthBitPerNs + ")");
    }

    @Override
    public Link generate(NetworkDevice fromNetworkDevice, NetworkDevice toNetworkDevice) {
        if (fromNetworkDevice.isServer() || toNetworkDevice.isServer()) {
            return new PerfectSimpleLink(delayNs, 1);
        } else {
            return new PerfectSimpleLink(delayNs, 4);
        }

    }

}
