import csv
import sys
import os
import random
import math as m

def analyze_inversions():
    blockings_utilizationSPPIFO = {}
    blockings_utilizationSPPIFO[0] = 0
    blockings_utilizationSPPIFO[1] = 0
    blockings_utilizationSPPIFO[2] = 0
    blockings_utilizationSPPIFO[3] = 0
    blockings_utilizationSPPIFO[4] = 0

    blockings_utilizationFIFO = {}
    blockings_utilizationFIFO[0] = 0
    blockings_utilizationFIFO[1] = 0
    blockings_utilizationFIFO[2] = 0
    blockings_utilizationFIFO[3] = 0
    blockings_utilizationFIFO[4] = 0

    blockings_utilizationGreedy = {}
    blockings_utilizationGreedy[0] = 0
    blockings_utilizationGreedy[1] = 0
    blockings_utilizationGreedy[2] = 0
    blockings_utilizationGreedy[3] = 0
    blockings_utilizationGreedy[4] = 0

    #SPPIFO
    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_20/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationSPPIFO[0] = blockings_utilizationSPPIFO[0] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_40/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationSPPIFO[1] = blockings_utilizationSPPIFO[1] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_60/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationSPPIFO[2] = blockings_utilizationSPPIFO[2] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_80/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationSPPIFO[3] = blockings_utilizationSPPIFO[3] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/SPPIFO_90/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationSPPIFO[4] = blockings_utilizationSPPIFO[4] + 1

    #Greedy
    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_20/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationGreedy[0] = blockings_utilizationGreedy[0] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_40/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationGreedy[1] = blockings_utilizationGreedy[1] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_60/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationGreedy[2] = blockings_utilizationGreedy[2] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_80/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationGreedy[3] = blockings_utilizationGreedy[3] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/Greedy_90/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationGreedy[4] = blockings_utilizationGreedy[4] + 1

    #FIFO
    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_20/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationFIFO[0] = blockings_utilizationFIFO[0] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_40/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationFIFO[1] = blockings_utilizationFIFO[1] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_60/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationFIFO[2] = blockings_utilizationFIFO[2] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_80/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationFIFO[3] = blockings_utilizationFIFO[3] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/utilization/FIFO_90/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            blockings_utilizationFIFO[4] = blockings_utilizationFIFO[4] + 1

    axis = [20,40,60,80,90]

    # Write results in file
    w = open("projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/utilization/utilization.dat", 'w')
    w.write("#  FIFO    SPPIFO  Greedy\n")
    for line in range(0,len(axis)):
        w.write("%s   %s   %s   %s\n" % (axis[line], blockings_utilizationFIFO[line], blockings_utilizationSPPIFO[line], blockings_utilizationGreedy[line]))

# Call analysis functions
analyze_inversions()