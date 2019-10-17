import csv
import sys
import os
import matplotlib.pyplot as plt

##################################
# Analyze rank distribution across queues
##################################

def analyze_rankmappings(folder, filename):
    with open(folder) as file:
        reader = csv.reader(file)

        packets0 = []
        packets1 = []
        packets2 = []
        packets3 = []
        packets4 = []
        packets5 = []
        packets6 = []
        packets7 = []

        for row in reader:
            port = float(row[0])
            rank = float(row[1])
            queue = float(row[2])

            if port == 1:
                if queue == 0:
                    packets0.append(rank)
                elif queue == 1:
                    packets1.append(rank)
                elif queue == 2:
                    packets2.append(rank)
                elif queue == 3:
                    packets3.append(rank)
                elif queue == 4:
                    packets4.append(rank)
                elif queue == 5:
                    packets5.append(rank)
                elif queue == 6:
                    packets6.append(rank)
                elif queue == 7:
                    packets7.append(rank)

        #n0 is the height of each bin in the histogram, bins0 is the position of the bin in the x axis
        n0, bins0, patches0 = plt.hist(packets0, bins=range(0, 101), histtype='step', label='Queue 0')
        n1, bins1, patches1 = plt.hist(packets1, bins=range(0, 101), histtype='step', label='Queue 1')
        n2, bins2, patches2 = plt.hist(packets2, bins=range(0, 101), histtype='step', label='Queue 2')
        n3, bins3, patches3 = plt.hist(packets3, bins=range(0, 101), histtype='step', label='Queue 3')
        n4, bins4, patches4 = plt.hist(packets4, bins=range(0, 101), histtype='step', label='Queue 4')
        n5, bins5, patches5 = plt.hist(packets5, bins=range(0, 101), histtype='step', label='Queue 5')
        n6, bins6, patches6 = plt.hist(packets6, bins=range(0, 101), histtype='step', label='Queue 6')
        n7, bins7, patches7 = plt.hist(packets7, bins=range(0, 101), histtype='step', label='Queue 7')

        w = open(filename, 'w')
        w.write("#    Queue0    Queue1  Queue2  Queue3  Queue4  Queue5  Queue6  Queue7\n")
        for line in range(0,len(n0)):
            w.write("%s   %s   %s   %s   %s   %s   %s   %s   %s\n" % (bins0[line], n0[line], n1[line], n2[line], n3[line], n4[line], n5[line], n6[line], n7[line]))


##################################
# Analyze queue-level evolution
##################################

def analyze_queuelevel(folder, filename):
    with open(folder) as file:
        reader = csv.reader(file)

        queuelevels0 = []
        queuelevels1 = []
        queuelevels2 = []
        queuelevels3 = []
        queuelevels4 = []
        queuelevels5 = []
        queuelevels6 = []
        queuelevels7 = []

        for row in reader:
            port = int(row[0])
            queue = int(row[1])
            level = int(row[2])

            if port == 1:
                if queue == 0:
                    queuelevels0.append(level)
                elif queue == 1:
                    queuelevels1.append(level)
                elif queue == 2:
                    queuelevels2.append(level)
                elif queue == 3:
                    queuelevels3.append(level)
                elif queue == 4:
                    queuelevels4.append(level)
                elif queue == 5:
                    queuelevels5.append(level)
                elif queue == 6:
                    queuelevels6.append(level)
                elif queue == 7:
                    queuelevels7.append(level)

        axis0 = range(0,len(queuelevels0))

        w = open(filename, 'w')
        w.write("#    Queue0    Queue1  Queue2  Queue3  Queue4  Queue5  Queue6  Queue7\n")
        for line in range(0,len(axis0)):
            w.write("%s   %s   %s   %s   %s   %s   %s   %s   %s\n" % (axis0[line], queuelevels0[line], queuelevels1[line], queuelevels2[line], queuelevels3[line], queuelevels4[line], queuelevels5[line], queuelevels6[line], queuelevels7[line]))

# Call analysis functions
analyze_rankmappings('temp/sppifo/greedy_convergence/Greedy_Convex/rank_mapping.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_convex_ranks.dat')
analyze_queuelevel('temp/sppifo/greedy_convergence/Greedy_Convex/queuebound_tracking.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_convex_queuebounds.dat')
analyze_rankmappings('temp/sppifo/greedy_convergence/Greedy_Exponential/rank_mapping.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_exponential_ranks.dat')
analyze_queuelevel('temp/sppifo/greedy_convergence/Greedy_Exponential/queuebound_tracking.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_exponential_queuebounds.dat')
analyze_rankmappings('temp/sppifo/greedy_convergence/Greedy_InverseExponential/rank_mapping.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_inverse_exponential_ranks.dat')
analyze_queuelevel('temp/sppifo/greedy_convergence/Greedy_InverseExponential/queuebound_tracking.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_inverse_exponential_queuebounds.dat')
analyze_rankmappings('temp/sppifo/greedy_convergence/Greedy_MinMax/rank_mapping.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_minmax_ranks.dat')
analyze_queuelevel('temp/sppifo/greedy_convergence/Greedy_MinMax/queuebound_tracking.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_minmax_queuebounds.dat')
analyze_rankmappings('temp/sppifo/greedy_convergence/Greedy_Poisson/rank_mapping.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_poisson_ranks.dat')
analyze_queuelevel('temp/sppifo/greedy_convergence/Greedy_Poisson/queuebound_tracking.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_poisson_queuebounds.dat')
analyze_rankmappings('temp/sppifo/greedy_convergence/Greedy_Uniform/rank_mapping.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_uniform_ranks.dat')
analyze_queuelevel('temp/sppifo/greedy_convergence/Greedy_Uniform/queuebound_tracking.csv.log', 'projects/sppifo/plots/greedy_convergence/greedy_uniform_queuebounds.dat')