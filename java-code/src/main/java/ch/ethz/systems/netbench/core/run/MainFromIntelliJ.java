package ch.ethz.systems.netbench.core.run;

public class MainFromIntelliJ {


    public static void main(String args[]) {

        /* Figure 5: SP-PIFO performance (uniform rank distribution) */

            /* Figure 5a: Uniform 8 queues */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/FIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/SPPIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/Fixed_queue_bounds.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/Greedy.properties"});

            /* Figure 5b: Uniform 32 queues */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_32_queues/FIFO_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_32_queues/SPPIFO_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_32_queues/Fixed_queue_bounds_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_32_queues/Greedy_32.properties"});

            /* Figure 5c: Adaptation strategies */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_queuebound.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_cost.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_rank.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_1.properties"});

            /* Figure 5d: Utilization */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_20.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_40.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_60.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_80.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_90.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_20.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_40.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_60.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_80.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_90.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_20.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_40.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_60.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_80.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_90.properties"});

            /* Analyze and plot */
            MainFromProperties.runCommand("python3 projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/analyze.py", false);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/plot.gnuplot", true);
            MainFromProperties.runCommand("python3 projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_32_queues/analyze.py", false);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_32_queues/plot.gnuplot", true);
            MainFromProperties.runCommand("python3 projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/analyze.py", false);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/plot.gnuplot", true);
            MainFromProperties.runCommand("python3 projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/utilization/analyze.py", false);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/utilization/plot.gnuplot", true);

        /* Figure 6: SP-PIFO performance (alternative distributions) */

            /* Figure 6a: Exponential */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/exponential/FIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/exponential/SPPIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/exponential/Greedy.properties"});

            /* Figure 6b: Inverse exponential */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/inverse_exponential/FIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/inverse_exponential/SPPIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/inverse_exponential/Greedy.properties"});

            /* Figure 6c: Poisson */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/poisson/FIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/poisson/SPPIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/poisson/Greedy.properties"});

            /* Figure 6d: Convex */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/convex/FIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/convex/SPPIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/alternative_distributions/convex/Greedy.properties"});

            /* Analyze and plot */
            MainFromProperties.runCommand("python3 projects/sppifo/plots/sppifo_analysis/alternative_distributions/analyze.py", false);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/alternative_distributions/exponential/plot.gnuplot", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/alternative_distributions/inverse_exponential/plot.gnuplot", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/alternative_distributions/poisson/plot.gnuplot", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/alternative_distributions/convex/plot.gnuplot", true);

        /* Figure 7: pFabric: FCT statistics across different flow sizes in data mining workload */

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/4000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/4000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/4000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/4000/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/6000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/6000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/6000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/6000/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/10000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/10000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/10000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/10000/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/15000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/15000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/15000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/15000/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/22500/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/22500/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/22500/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/22500/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/37000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/37000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/37000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/37000/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/60000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/60000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/60000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/data_mining_workload/60000/SPPIFO.properties"});

            /* Analyze and plot */
            MainFromProperties.runCommand("python3 projects/sppifo/plots/sppifo_evaluation/pFabric/data_mining_workload/analyze.py", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_evaluation/pFabric/data_mining_workload/plot.gnuplot", true);

        /* Figure 8: pFabric: FCT statistics across different flow sizes in web search workload */

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/3600/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/3600/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/3600/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/3600/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/5200/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/5200/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/5200/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/5200/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/7000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/7000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/7000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/7000/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/8900/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/8900/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/8900/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/8900/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/11100/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/11100/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/11100/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/11100/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/14150/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/14150/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/14150/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/14150/SPPIFO.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/19000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/19000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/19000/PIFO.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/pFabric/web_search_workload/19000/SPPIFO.properties"});

            /* Analyze and plot */
            MainFromProperties.runCommand("python3 projects/sppifo/plots/sppifo_evaluation/pFabric/web_search_workload/analyze.py", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_evaluation/pFabric/web_search_workload/plot.gnuplot", true);

        /* Figure 9 and 10: Fairness FCT statistics */

            /* Figure 9: Fairness: FCT statistics for all flows at different loads, over the web search workload */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/PIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/3600/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/PIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/5200/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/PIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/7000/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/PIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/8900/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/PIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/11100/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/PIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/14150/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/TCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/DCTCP.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/PIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/19000/SPPIFOWFQ_32.properties"});

            /* Figure 10: Fairness: FCT statistics for all flows at different loads, when the number of queues is modified */
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/AFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/AFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/SPPIFOWFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/SPPIFOWFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/3600/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/AFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/AFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/SPPIFOWFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/SPPIFOWFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/7000/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/AFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/AFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/SPPIFOWFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/SPPIFOWFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/11100/SPPIFOWFQ_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/AFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/AFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/AFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/AFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/PIFOWFQ_32.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/SPPIFOWFQ_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/SPPIFOWFQ_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/SPPIFOWFQ_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_evaluation/fairness/web_search_workload/queue_analysis/19000/SPPIFOWFQ_32.properties"});

            /* Analyze and plot */
            MainFromProperties.runCommand("python projects/sppifo/plots/sppifo_evaluation/fairness/analyze.py", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_evaluation/fairness/plot.gnuplot", true);

        /* Figure 13: Greedy convergence for uniform rank distribution (we add extra distributions that are not included in the paper) */

            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_convergence/Greedy_Uniform.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_convergence/Greedy_Exponential.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_convergence/Greedy_InverseExponential.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_convergence/Greedy_MinMax.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_convergence/Greedy_Poisson.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_convergence/Greedy_Convex.properties"});

            /* Analyze and plot */
            MainFromProperties.runCommand("python3 projects/sppifo/plots/greedy_convergence/analyze.py", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/greedy_convergence/plot.gnuplot", true);

        /* Figure 14: Greedy algorithm adaptation microbenchmark */

            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/adaptation_period/Greedy_Uniform_50.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/adaptation_period/Greedy_Uniform_250.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/adaptation_period/Greedy_Uniform_500.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/adaptation_period/Greedy_Uniform_1000.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/adaptation_period/Greedy_Uniform_2500.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/adaptation_period/Greedy_Uniform_5000.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/adaptation_period/Greedy_Uniform_7000.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_4.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_8.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_12.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_16.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_20.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_24.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_28.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/num_queues/Greedy_Uniform_32.properties"});

            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/max_rank/Greedy_Uniform_50.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/max_rank/Greedy_Uniform_100.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/max_rank/Greedy_Uniform_200.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/max_rank/Greedy_Uniform_400.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/max_rank/Greedy_Uniform_600.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/max_rank/Greedy_Uniform_800.properties"});
            MainFromProperties.main(new String[]{"projects/sppifo/runs/greedy_microbenchmark/max_rank/Greedy_Uniform_1000.properties"});

            /* Analyze and plot */
            MainFromProperties.runCommand("python3 projects/sppifo/plots/greedy_microbenchmark/analyze.py", true);
            MainFromProperties.runCommand("gnuplot projects/sppifo/plots/greedy_microbenchmark/plot.gnuplot", true);

    }

}
