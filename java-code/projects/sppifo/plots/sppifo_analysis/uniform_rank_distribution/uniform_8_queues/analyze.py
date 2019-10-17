import csv
import sys
import os
import random
import math as m

def analyze_inversions():
    inversionsFIFO = {}
    inversionsSPPIFO = {}
    inversionsGreedy = {}
    inversionsFixed_queue_bounds = {}

    for i in range(0,101):
        inversionsFIFO[i] = 0
        inversionsSPPIFO[i] = 0
        inversionsGreedy[i] = 0
        inversionsFixed_queue_bounds[i] = 0

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/FIFO/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsFIFO[rank] = inversionsFIFO[rank] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/SPPIFO/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsSPPIFO[rank] = inversionsSPPIFO[rank] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/Greedy/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsGreedy[rank] = inversionsGreedy[rank] + 1

    with open("temp/sppifo/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/Fixed_queue_bounds/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsFixed_queue_bounds[rank] = inversionsFixed_queue_bounds[rank] + 1

    # Write results in file
    w = open("projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/uniform.dat", 'w')
    axis = range(0,100)
    w.write("#  FIFO    SPPIFO  Greedy Fixed_queue_bounds\n")
    for line in range(0,len(axis)):
        w.write("%s   %s   %s   %s   %s\n" % (axis[line], inversionsFIFO[line], inversionsSPPIFO[line], inversionsGreedy[line], inversionsFixed_queue_bounds[line]))


# Call analysis functions
analyze_inversions()
