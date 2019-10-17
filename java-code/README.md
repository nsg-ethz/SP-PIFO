
# SP-PIFO: Analysis and performance evaluation

## Getting started with [NetBench](https://github.com/ndal-eth/netbench)

#### 1. Software dependencies

* **Java 8:** Version 8 of Java; both Oracle JDK and OpenJDK are supported and produce under that same seed deterministic results. Additionally the project uses the Apache Maven software project management and comprehension tool (version 3+).

* **Python 2 (optional):** Recent version of Python 2 for analysis; be sure you can globally run `python <script.py>`.

#### 2. Building

1. Compile and run all tests in the project, make sure that they all pass; this can be done using the following maven command: `mvn compile test`

2. Build the executable `NetBench.jar` by using the following maven command: `mvn clean compile assembly:single`

#### 3. Running

1. Execute a demo run by using the following command: `java -jar -ea NetBench.jar ./example/runs/demo.properties`

2. After the run, the log files are saved in the `./temp/demo` folder

3. If you have python 2 installed, you can view calculated statistics about flow completion and port utilization (e.g. mean FCT, 99th %-tile port utilization, ....) in the `./temp/demo/analysis` folder.

## Software structure

There are three sub-packages in *netbench*: (a) core, containing core functionality, (b) ext (extension), which contains functionality implemented and quite thoroughly tested, and (c) xpt (experimental), which contains functionality not yet as thoroughly tested but reasonably vetted and assumed to be correct for the usecase it was written for.

The framework is written based on five core components:
1. **Network device**: abstraction of a node, can be a server (has a transport layer) or merely function as switch (no transport layer);
2. **Transport layer**: maintains the sockets for each of the flows that are started at the network device and for which it is the destination;
3. **Intermediary**: placed between the network device and transport layer, is able to modify each packet before arriving at the transport layer and after leaving the transport layer;
4. **Link**: quantifies the capabilities of the physical link, which the output port respects;
5. **Output port**: models output ports and their queueing behavior.

Look into `ch.ethz.systems.netbench.ext.demo` for an impression how to extend the framework.  If you've written an extension, it is necessary to add it in its respective selector in `ch.ethz.systems.netbench.run`. If you've added new properties, be sure to add them in the `ch.ethz.systems.netbench.config.BaseAllowedProperties` class.

More information about the framework can be found in the thesis located at [https://www.research-collection.ethz.ch/handle/20.500.11850/156350](https://www.research-collection.ethz.ch/handle/20.500.11850/156350) (section 4.2: NetBench: Discrete Packet Simulator).

---

## Reproducing the results in "SP-PIFO: Approximating Push-In First-Out Behaviors using Strict-Priority Queues"

#### General remarks about structure

1. Make sure you understand and ran through the Getting Started section above. 

2. SP-PIFO files are placed within the `./projects/sppifo` folder, which aims to be separated from the original [NetBench](https://github.com/ndal-eth/netbench) code for the sake of modularity.

* **Run configurations**:  All run configurations are placed in the `./projects/sppifo/runs` folder, organized based on the Figures presented in the paper:

    * `./projects/sppifo/runs/sppifo_analysis` contains the configurations for experiments in *Section 4.2: SP-PIFO Analysis*.
    * `./projects/sppifo/runs/sppifo_evaluation` contains the configurations for experiments in *Section 6.1: Performance Analysis*.
    * `./projects/sppifo/runs/greedy_microbenchmark` contains the configurations for experiments in *Appendix A: Gradient-based Algorithm*.
    * `./projects/sppifo/runs/greedy_convergence` contains the configurations for experiments in *Appendix A.4: Convergence Analysis*.

 * **Output simulations**: The output of the runs are written to the `./temp/sppifo` folder, and organized with the same corresponding sub-folders:

    * `./temp/sppifo/sppifo_analysis`
    * `./temp/sppifo/sppifo_evaluation`
    * `./temp/sppifo/greedy_microbenchmark`
    * `./temp/sppifo/greedy_convergence`

 * **Result analysis and plots**: The scripts used to analyze the simulation results, `analyze.py`, and to generate the paper plots, `plot.gnuplot`, are placed in the `./projects/sppifo/plots` folder, under the same organizational structure:

    * `./projects/sppifo/plots/sppifo_analysis/uniform_rank_distributions` contains the results for Figure 5.
    * `./projects/sppifo/plots/sppifo_analysis/alternative_distributions` contains the results for Figure 6.
    * `./projects/sppifo/plots/sppifo_evaluation/pFabric` contains the results for Figure 8 and 9. 
    * `./projects/sppifo/plots/sppifo_evaluation/fairness` contains the results for Figure 10. 
    * `./projects/sppifo/plots/greedy_microbenchmark` contains the results for Figure 13.
    * `./projects/sppifo/plots/greedy_convergence` contains the results for Figure 14.

 * **Main file to run the simulations**: The simulations can be executed by running the file `MainFromIntelliJ.java` file, located in `src/main/java/ch.ethz.systems.netbench/core/run`. This file is responsible for (i) executing all the simulations as configured in `./projects/sppifo/runs`,  (ii) generating the output results for those simulations in `./temp/sppifo/`, and (iii) analyzing those results to generate the plots in `./projects/sppifo/plots`.

    * Pro tip: Just import the `pom.xml` file to your favorite SDK (we used [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)), which provides all the configuration for the Maven project. 

#### SP-PIFO extensions to the original NetBench

In order to perform the simulations presented in the SP-PIFO paper, we needed to add some extensions to the original NetBench simulator. We detail those extensions in the following lines. 

 * **Output ports and transport layers**: They can be found within the *xpt (experimental)* sub-package in the main source code of the simulator (i.e., `src.main.java.ch.ethz.systems.netbench.xpt`). In particular:

    * `xpt/sppifo/ports` contains the implementations of the scheduling algorithms used on our simulations which were not available in the original distribution (e.g., FIFO, PIFO, AFQ, SP-PIFO, and Greedy).
    * `xpt/vojislav_and_sppifo` contains some of the transport layers we used in our experiments (e.g, pFabric, LSTF). They were part of the original distribution but we had to extend them according to our needs.
    * `core/config/exceptions/BaseAllowedProperties` and `core/config/run/traffic/InfrastructureSelector` contain the configuration parameters which are required to use those output ports and transport layers. 

 * **Loggers**: We have added new loggers to track (i) the dynamic mapping of packet ranks to priority queues, (ii) the evolution of queue bounds, (iii) the unpifoness, and (iv) the amount of inversions generated per ranks in each of the presented scheduling algorithms. 

    * `core/config/log/SimulationLogger` contains those extra loggers with their respective configurations. 

#### Replotting without rerunning

For convenience, we've added all the processed results and the plots without the logs (which are too big for git) in the `./projects/sppifo/plots/` folder. As soon as the `MainFromIntelliJ.java` file is executed, the logs will be automatically generated for each simulation and the plots will be rerun with the results of the new simulations. Feel free to adapt the `MainFromIntelliJ.java` file to select which of the individual Figures you want to replot.

#### Run example (Figure 5a: Uniform 8 queues)

Let's now go for an example, wanting to reproduce the experiments required to reproduce Figure 5a.

1. Look into folder `projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/`. It contains the various `*.property` run configuration files that correspond to Figure 5a. 

2. Execute those simulations by pointing out their configuration files. They can be called individually or directly from a script or the `MainFromIntelliJ.java` file:
```javascript
    /* Figure 5a: Uniform 8 queues */
    MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/FIFO.properties"});
    MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/SPPIFO.properties"});
    MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/Fixed_queue_bounds.properties"});
    MainFromProperties.main(new String[]{"projects/sppifo/runs/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/Greedy.properties"});
```

3. Look into folder `temp/sppifo/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/`, it contains the raw log files for each of the run simulations.

4. Analyze those log files and process them to generate the plots. 

```javascript
    /* Analyze and plot */
    MainFromProperties.runCommand("python3 projects/sppifo/plots//uniform_rank_distribution/uniform_8_queues/analyze.py", false);
    MainFromProperties.runCommand("gnuplot projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/plot.gnuplot", true);
```

5. Look into folder `projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/`, which contains the final plots. 

6. Feel free to modify the configuration files and repeat the process to see the effects of your changes to the final results. 