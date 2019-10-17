# SP-PIFO: Approximating Push-In First-Out Behaviors <br/> using Strict-Priority Queues

This repository contains the code used in [SP-PIFO](https://nsg.ee.ethz.ch/fileadmin/user_upload/SP-PIFO.pdf), which was accepted at [NSDI'20](https://www.usenix.org/conference/nsdi20/accepted-papers).

## What can I find in this repo?

* `java_code` contains the Java-based implementation of SP-PIFO, which is built on top of [NetBench](https://github.com/ndal-eth/netbench).
We have used this implementation to evaluate SP-PIFO performance when approximating well-known scheduling objectives under realistic traffic workloads. This is the code used for the evaluation in Section 4.2, Section 6.1 and Appendix A. 

* `p4_code` contains the P4_16 code to run SP-PIFO on programmable switches.<br/>
The code can be directly deployed on a software switch (e.g., [P4 behavioral model](https://github.com/p4lang/behavioral-model) in [Mininet](http://mininet.org)), or adapted to be executed on a hardware switch (e.g., [Barefoot Tofino](https://www.barefootnetworks.com/products/brief-tofino/)). This is the code used to make the plots of Section 6.2.

 ## Contact

Please, send us an e-mail to: galberto@ethz.ch or adietmue@ethz.ch,
- If you are interested in collaborating with the project.
- If you are having issues when trying to run the experiments described on the paper.
- If you happen to find a bug.
- If you have any other question or concern :)
