package ch.ethz.systems.netbench.ext.basic;

import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.run.infrastructure.LinkGenerator;

public class PerfectSimpleLinkGenerator extends LinkGenerator {

    private final long delayNs;
    private final double bandwidthBitPerNs;

    public PerfectSimpleLinkGenerator(long delayNs, double bandwidthBitPerNs) {
        this.delayNs = delayNs;
        this.bandwidthBitPerNs = bandwidthBitPerNs;
        SimulationLogger.logInfo("Link", "PERFECT_SIMPLE_LINK(delayNs=" + delayNs + ", bandwidthBitPerNs=" + bandwidthBitPerNs + ")");
    }

    @Override
    public Link generate(NetworkDevice fromNetworkDevice, NetworkDevice toNetworkDevice) {
        return new PerfectSimpleLink(delayNs, bandwidthBitPerNs);
    }

}
