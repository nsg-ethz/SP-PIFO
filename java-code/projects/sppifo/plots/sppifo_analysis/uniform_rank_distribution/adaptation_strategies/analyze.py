import csv
import sys
import os
import random
import math as m

def analyze_adaptation_strategies():
    inversionsSPPIFO_1 = {}
    inversionsSPPIFO_cost = {}
    inversionsSPPIFO_queuebound = {}
    inversionsSPPIFO_rank = {}

    for i in range(0,101):
        inversionsSPPIFO_1[i] = 0
        inversionsSPPIFO_cost[i] = 0
        inversionsSPPIFO_queuebound[i] = 0
        inversionsSPPIFO_rank[i] = 0

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_1/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsSPPIFO_1[rank] = inversionsSPPIFO_1[rank] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_cost/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsSPPIFO_cost[rank] = inversionsSPPIFO_cost[rank] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_queuebound/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsSPPIFO_queuebound[rank] = inversionsSPPIFO_queuebound[rank] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/SPPIFO_rank/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsSPPIFO_rank[rank] = inversionsSPPIFO_rank[rank] + 1

    # Write results in file
    w = open("projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/adaptation_strategies.dat", 'w')
    axis = range(0,100)
    w.write("#  SPPIFO_1    SPPIFO_cost  SPPIFO_queuebound SPPIFO_rank\n")
    for line in range(0,len(axis)):
        w.write("%s   %s   %s   %s   %s\n" % (axis[line], inversionsSPPIFO_1[line], inversionsSPPIFO_cost[line], inversionsSPPIFO_queuebound[line], inversionsSPPIFO_rank[line]))

# Call analysis functions
analyze_adaptation_strategies()