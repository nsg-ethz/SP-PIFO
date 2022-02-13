package ch.ethz.systems.netbench.core.run;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.config.exceptions.PropertyValueInvalidException;
import ch.ethz.systems.netbench.core.run.infrastructure.IntermediaryGenerator;
import ch.ethz.systems.netbench.core.run.infrastructure.LinkGenerator;
import ch.ethz.systems.netbench.core.run.infrastructure.NetworkDeviceGenerator;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;
import ch.ethz.systems.netbench.core.run.infrastructure.TransportLayerGenerator;
import ch.ethz.systems.netbench.ext.bare.BareTransportLayerGenerator;
import ch.ethz.systems.netbench.ext.basic.EcnTailDropOutputPortGenerator;
import ch.ethz.systems.netbench.ext.basic.PerfectSimpleLinkGenerator;
import ch.ethz.systems.netbench.ext.basic.SplitBandwidthLinkGenerator;
import ch.ethz.systems.netbench.ext.basic.TailDropOutputPortGenerator;
import ch.ethz.systems.netbench.ext.demo.DemoIntermediaryGenerator;
import ch.ethz.systems.netbench.ext.demo.DemoTransportLayerGenerator;
import ch.ethz.systems.netbench.ext.ecmp.EcmpSwitchGenerator;
import ch.ethz.systems.netbench.ext.ecmp.ForwarderSwitchGenerator;
import ch.ethz.systems.netbench.ext.flowlet.IdentityFlowletIntermediaryGenerator;
import ch.ethz.systems.netbench.ext.flowlet.UniformFlowletIntermediaryGenerator;
import ch.ethz.systems.netbench.ext.hybrid.EcmpThenValiantSwitchGenerator;
import ch.ethz.systems.netbench.ext.valiant.RangeValiantSwitchGenerator;
import ch.ethz.systems.netbench.xpt.ports.AFQ.AFQOutputPortGenerator;
import ch.ethz.systems.netbench.xpt.ports.FIFO.FIFOOutputPortGenerator;
import ch.ethz.systems.netbench.xpt.ports.PIFO.PIFOOutputPortGenerator;
import ch.ethz.systems.netbench.xpt.ports.SPPIFO.SPPIFOOutputPortGenerator;
import ch.ethz.systems.netbench.xpt.ports.Greedy.GreedyOutputPortGenerator_Advanced;
import ch.ethz.systems.netbench.xpt.ports.Greedy.GreedyOutputPortGenerator_Simple;
import ch.ethz.systems.netbench.xpt.ports.SPPIFO_WFQ.WFQSPPIFOOutputPortGenerator;
import ch.ethz.systems.netbench.xpt.ports.PIFO_WFQ.WFQPIFOOutputPortGenerator;
import ch.ethz.systems.netbench.xpt.asaf.routing.priority.PriorityFlowletIntermediaryGenerator;
import ch.ethz.systems.netbench.xpt.newreno.newrenodctcp.NewRenoDctcpTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.newreno.newrenotcp.NewRenoTcpTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.simple.simpledctcp.SimpleDctcpTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.simple.simpletcp.SimpleTcpTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.sourcerouting.EcmpThenSourceRoutingSwitchGenerator;
import ch.ethz.systems.netbench.xpt.sourcerouting.SourceRoutingSwitchGenerator;
import ch.ethz.systems.netbench.xpt.tcpextended.buffertcp.BufferTcpTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.tcpextended.lstftcp.LstfTcpTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.tcpextended.pfabric.PfabricTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.tcpextended.pfzero.PfzeroTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.tcpextended.sphalftcp.SpHalfTcpTransportLayerGenerator;
import ch.ethz.systems.netbench.xpt.tcpextended.sptcp.SpTcpTransportLayerGenerator;

class InfrastructureSelector {

    private InfrastructureSelector() {
        // Only static class
    }

    /**
     * Select the network device generator, which, given its identifier,
     * generates an appropriate network device (possibly with transport layer).
     *
     * Selected using following properties:
     * network_device=...
     * network_device_intermediary=...
     *
     * @return  Network device generator.
     */
    static NetworkDeviceGenerator selectNetworkDeviceGenerator() {

        /*
         * Select intermediary generator.
         */
        IntermediaryGenerator intermediaryGenerator;
        switch (Simulator.getConfiguration().getPropertyOrFail("network_device_intermediary")) {

            case "demo": {
                intermediaryGenerator = new DemoIntermediaryGenerator();
                break;
            }

            case "identity": {
                intermediaryGenerator = new IdentityFlowletIntermediaryGenerator();
                break;
            }

            case "uniform": {
                intermediaryGenerator = new UniformFlowletIntermediaryGenerator();
                break;
            }

            case "low_high_priority": {
                intermediaryGenerator = new PriorityFlowletIntermediaryGenerator();
                break;
            }

            default:
                throw new PropertyValueInvalidException(
                        Simulator.getConfiguration(),
                        "network_device_intermediary"
                );

        }

        /*
         * Select network device generator.
         */
        switch (Simulator.getConfiguration().getPropertyOrFail("network_device")) {

            case "forwarder_switch":
                return new ForwarderSwitchGenerator(intermediaryGenerator, Simulator.getConfiguration().getGraphDetails().getNumNodes());

            case "ecmp_switch":
                return new EcmpSwitchGenerator(intermediaryGenerator, Simulator.getConfiguration().getGraphDetails().getNumNodes());

            case "random_valiant_ecmp_switch":
                return new RangeValiantSwitchGenerator(intermediaryGenerator, Simulator.getConfiguration().getGraphDetails().getNumNodes());

            case "ecmp_then_random_valiant_ecmp_switch":
                return new EcmpThenValiantSwitchGenerator(intermediaryGenerator, Simulator.getConfiguration().getGraphDetails().getNumNodes());

            case "source_routing_switch":
                return new SourceRoutingSwitchGenerator(intermediaryGenerator, Simulator.getConfiguration().getGraphDetails().getNumNodes());

            case "ecmp_then_source_routing_switch":
                return new EcmpThenSourceRoutingSwitchGenerator(intermediaryGenerator, Simulator.getConfiguration().getGraphDetails().getNumNodes());

            default:
                throw new PropertyValueInvalidException(
                        Simulator.getConfiguration(),
                        "network_device"
                );

        }

    }

    /**
     * Select the link generator which creates a link instance given two
     * directed network devices.
     *
     * Selected using following property:
     * link=...
     *
     * @return  Link generator
     */
    static LinkGenerator selectLinkGenerator() {

        switch (Simulator.getConfiguration().getPropertyOrFail("link")) {

            case "perfect_simple":
                return new PerfectSimpleLinkGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("link_delay_ns"),
                        Simulator.getConfiguration().getDoublePropertyOrFail("link_bandwidth_bit_per_ns")
                );

            case "split_bw":
                return new SplitBandwidthLinkGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("link_delay_ns"),
                        Simulator.getConfiguration().getDoublePropertyOrFail("link_bandwidth_bit_per_ns")
                );

            default:
                throw new PropertyValueInvalidException(
                        Simulator.getConfiguration(),
                        "link"
                );

        }

    }

    /**
     * Select the output port generator which creates a port instance given two
     * directed network devices and the corresponding link.
     *
     * Selected using following property:
     * output_port=...
     *
     * @return  Output port generator
     */
    static OutputPortGenerator selectOutputPortGenerator() {

        switch (Simulator.getConfiguration().getPropertyOrFail("output_port")) {

            case "tail_drop":
                return new TailDropOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_queue_size_bytes")
                );

            case "ecn_tail_drop":
                return new EcnTailDropOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_queue_size_bytes"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_ecn_threshold_k_bytes")
                );

            case "sppifo":
                return new SPPIFOOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_number_queues"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_per_queue_packets"),
                        Simulator.getConfiguration().getPropertyOrFail("output_port_step_size")
                );

            case "greedy_simple":
                return new GreedyOutputPortGenerator_Simple(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_number_queues"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_per_queue_packets"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_adaptation_period"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_rank")
                );

            case "greedy_advanced":
                return new GreedyOutputPortGenerator_Advanced(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_number_queues"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_per_queue_packets"),
                        Simulator.getConfiguration().getPropertyOrFail("output_port_initialization"),
                        Simulator.getConfiguration().getPropertyOrFail("output_port_fix_queue_bounds")
                );

            case "pifo":
                return new PIFOOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_packets")
                );

            case "wfqsppifo":
                return new WFQSPPIFOOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_number_queues"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_per_queue_packets")
                );

            case "wfqpifo":
                return new WFQPIFOOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_packets")
                );

            case "fifo":
                return new FIFOOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_packets")
                );

            case "afq":
                return new AFQOutputPortGenerator(
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_number_queues"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_max_size_per_queue_packets"),
                        Simulator.getConfiguration().getLongPropertyOrFail("output_port_bytes_per_round")
                );


            default:
                throw new PropertyValueInvalidException(
                        Simulator.getConfiguration(),
                        "output_port"
                );

        }

    }

    /**
     * Select the transport layer generator.
     *
     * @return  Transport layer generator
     */
    static TransportLayerGenerator selectTransportLayerGenerator() {

        switch (Simulator.getConfiguration().getPropertyOrFail("transport_layer")) {

            case "demo":
                return new DemoTransportLayerGenerator();

            case "bare":
                return new BareTransportLayerGenerator();

            case "tcp":
                return new NewRenoTcpTransportLayerGenerator();

            case "lstf_tcp":
                return new LstfTcpTransportLayerGenerator(
                        Simulator.getConfiguration().getPropertyOrFail("transport_layer_rank_distribution"),
                        Simulator.getConfiguration().getLongPropertyOrFail("transport_layer_rank_bound")
                );

            case "sp_tcp":
                return new SpTcpTransportLayerGenerator();

            case "sp_half_tcp":
                return new SpHalfTcpTransportLayerGenerator();

            case "pfabric":
                return new PfabricTransportLayerGenerator();
                
            case "pfzero":
                return new PfzeroTransportLayerGenerator();
                
            case "buffertcp":
                return new BufferTcpTransportLayerGenerator();
            
                case "dctcp":
                return new NewRenoDctcpTransportLayerGenerator();

            case "simple_tcp":
                return new SimpleTcpTransportLayerGenerator();

            case "simple_dctcp":
                return new SimpleDctcpTransportLayerGenerator();

            default:
                throw new PropertyValueInvalidException(
                        Simulator.getConfiguration(),
                        "transport_layer"
                );

        }

    }

}
