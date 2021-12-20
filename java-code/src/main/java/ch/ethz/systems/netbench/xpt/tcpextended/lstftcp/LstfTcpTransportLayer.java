package ch.ethz.systems.netbench.xpt.tcpextended.lstftcp;


import ch.ethz.systems.netbench.core.network.Socket;
import ch.ethz.systems.netbench.core.network.TransportLayer;

public class LstfTcpTransportLayer extends TransportLayer {

    private String rankDistribution;
    private long rankBound;

    /**
     * Create the TCP transport layer with the given network device identifier.
     * The network device identifier is used to create unique flow identifiers.
     *
     * @param identifier        Parent network device identifier
     */
    public LstfTcpTransportLayer(int identifier, String rankDistribution, long rankBound) {
        super(identifier);
        this.rankDistribution = rankDistribution;
        this.rankBound = rankBound;
    }

    @Override
    protected Socket createSocket(long flowId, int destinationId, long flowSizeByte) {
        return new LstfTcpSocket(this, flowId, this.identifier, destinationId, flowSizeByte, rankDistribution, rankBound);
    }

}
