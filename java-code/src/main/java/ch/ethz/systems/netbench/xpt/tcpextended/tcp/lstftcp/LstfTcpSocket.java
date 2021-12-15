package ch.ethz.systems.netbench.xpt.voijslav_and_sppifo.tcp.lstftcp;


import ch.ethz.systems.netbench.core.network.TransportLayer;
import ch.ethz.systems.netbench.xpt.newreno.newrenotcp.NewRenoTcpSocket;
import ch.ethz.systems.netbench.xpt.tcpbase.FullExtTcpPacket;

import java.util.Random;


public class LstfTcpSocket extends NewRenoTcpSocket {

    private String rankDistribution;
    private long rankBound;

    public LstfTcpSocket(TransportLayer transportLayer, long flowId, int sourceId, int destinationId, long flowSizeByte, String rankDistribution, long rankBound) {
        super(transportLayer, flowId, sourceId, destinationId, flowSizeByte);
        this.rankDistribution = rankDistribution;
        this.rankBound = rankBound;
    }

	/**
     * Create a TCP packet. Used internally as some flags are not needed to specify every time. Override just
     * because we need to add priority
     *
     * @param dataSizeByte      Data size carried in bytes
     * @param sequenceNumber    Sequence number
     * @param ackNumber         Acknowledgment number
     * @param ACK               True iff carries acknowledgment
     * @param SYN               True iff is a synchronization packet
     * @param ECE               True iff it wants to let other party know there congestion was encountered
     *
     * @return  TCP packet instance
     */
    @Override
    protected FullExtTcpPacket createPacket(
        long dataSizeByte,
        long sequenceNumber,
        long ackNumber,
        boolean ACK,
        boolean SYN,
        boolean ECE
    ) {
        long priority = 0;

        if (rankDistribution.equals("uniform")){
            Random independentRng = new Random();
            double outcome = independentRng.nextDouble()*this.rankBound; //Ranks distribuits uniformement de 1 a 100
            priority = (long)outcome;

        } else if (rankDistribution.equals("exponential")){
            RandomNumberGenerator testStat = RandomNumberGenerator.Exponential;
            double lambda = 1.0 / 25.0;
            int result = (int) testStat.getRandom(lambda);
            while (result > 99) {
                result = (int) testStat.getRandom(lambda);
            }
            priority = (long)result;

        } else if (rankDistribution.equals("inverse_exponential")){
            RandomNumberGenerator testStat = RandomNumberGenerator.Exponential;
            double lambda = 1.0 / 25.0;
            int result = (int) testStat.getRandom(lambda);
            while (result > 99) {
                result = (int) testStat.getRandom(lambda);
            }
            result = 100 - result;
            priority = (long) (Math.abs(result));

        } else if (rankDistribution.equals("poisson")){
            RandomNumberGenerator testStat = RandomNumberGenerator.Poisson;
            double lambda = 50;
            int result = (int) testStat.getRandom(lambda);
            priority = (long) Math.abs(result);

        } else if (rankDistribution.equals(("minmax"))){
            RandomNumberGenerator testStat = RandomNumberGenerator.Poisson;
            double lambda = 50;
            int result = (int) testStat.getRandom(lambda) - 10;
            result = Math.abs(result % 50);
            priority = (long) Math.abs(result);

        } else if (rankDistribution.equals("convex")){
            RandomNumberGenerator testStat = RandomNumberGenerator.Poisson;
            double lambda = 100;
            int result = (int) testStat.getRandom(lambda);
            result = Math.abs(result % 100);
            priority = (long) Math.abs(result);
        }

        return new FullExtTcpPacket(
            flowId, dataSizeByte, sourceId, destinationId,
            100, 80, 80, // TTL, source port, destination port
            sequenceNumber, ackNumber, // Seq number, Ack number
            false, false, ECE, // NS, CWR, ECE
            false, ACK, false, // URG, ACK, PSH
            false, SYN, false, // RST, SYN, FIN
            0, // Window size
            priority
        );
    }

}
