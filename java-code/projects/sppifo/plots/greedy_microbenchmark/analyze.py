import numpy as np
import csv
import sys
import os

##################################
# Varying adaptation period
##################################

def analyze_adaptation_period():
    unpifoness50 = []
    unpifoness250 = []
    unpifoness500 = []
    unpifoness1000 = []
    unpifoness2500 = []
    unpifoness5000 = []
    unpifoness7000 = []

    with open("temp/sppifo/greedy_microbenchmark/adaptation_period/Greedy_Uniform_50/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness50.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/adaptation_period/Greedy_Uniform_250/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness250.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/adaptation_period/Greedy_Uniform_500/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness500.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/adaptation_period/Greedy_Uniform_1000/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness1000.append(current_unpifoness)


    with open("temp/sppifo/greedy_microbenchmark/adaptation_period/Greedy_Uniform_2500/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness2500.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/adaptation_period/Greedy_Uniform_5000/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness5000.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/adaptation_period/Greedy_Uniform_7000/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness7000.append(current_unpifoness)

    # Write results in file
    w = open('projects/sppifo/plots/greedy_microbenchmark/unpifoness_adaptation_period.dat', 'w')
    axis = range(0,100)
    w.write("#    50  250  500  1000  2500  5000  7000\n")
    for line in range(0,len(axis)):
        w.write("%s   %s   %s   %s   %s   %s   %s   %s\n" % (axis[line], unpifoness50[line], unpifoness250[line], unpifoness500[line], unpifoness1000[line], unpifoness2500[line], unpifoness5000[line], unpifoness7000[line]))

##################################
# Varying number of queues
##################################

def analyze_queues():
    unpifoness8 = []
    unpifoness12 = []
    unpifoness16 = []
    unpifoness20 = []
    unpifoness24 = []
    unpifoness28 = []
    unpifoness32 = []

    with open("temp/sppifo/greedy_microbenchmark/num_queues/Greedy_Uniform_8/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness8.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/num_queues/Greedy_Uniform_12/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness12.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/num_queues/Greedy_Uniform_16/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness16.append(current_unpifoness)


    with open("temp/sppifo/greedy_microbenchmark/num_queues/Greedy_Uniform_20/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness20.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/num_queues/Greedy_Uniform_24/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness24.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/num_queues/Greedy_Uniform_28/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness28.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/num_queues/Greedy_Uniform_32/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness32.append(current_unpifoness)

    # Write results in file
    w = open('projects/sppifo/plots/greedy_microbenchmark/unpifoness_queues.dat', 'w')
    axis = range(0,100)
    w.write("#    8  12  16  20  24  28  32\n")
    for line in range(0,len(axis)):
        w.write("%s   %s   %s   %s   %s   %s   %s   %s\n" % (axis[line], unpifoness8[line], unpifoness12[line], unpifoness16[line], unpifoness20[line], unpifoness24[line], unpifoness28[line], unpifoness32[line]))

##################################
# Varying number of ranks
##################################

def analyze_ranks():
    unpifoness100 = []
    unpifoness200 = []
    unpifoness400 = []
    unpifoness600 = []
    unpifoness800 = []
    unpifoness1000 = []

    with open("temp/sppifo/greedy_microbenchmark/max_rank/Greedy_Uniform_100/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness100.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/max_rank/Greedy_Uniform_200/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness200.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/max_rank/Greedy_Uniform_400/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness400.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/max_rank/Greedy_Uniform_600/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness600.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/max_rank/Greedy_Uniform_800/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness800.append(current_unpifoness)

    with open("temp/sppifo/greedy_microbenchmark/max_rank/Greedy_Uniform_1000/unpifoness_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = float(row[0])
            current_unpifoness = float(row[1])
            if port == 1:
                unpifoness1000.append(current_unpifoness)

    # Write results in file
    w = open('projects/sppifo/plots/greedy_microbenchmark/unpifoness_ranks.dat', 'w')
    axis = range(0,1000)
    w.write("#    100  200  400  600  800  1000\n")
    for line in range(0,len(axis)):
        w.write("%s   %s   %s   %s   %s   %s   %s\n" % (axis[line], unpifoness100[line], unpifoness200[line], unpifoness400[line], unpifoness600[line], unpifoness800[line], unpifoness1000[line]))

    # A rank range higher than 5000 gives a too high number of inversions in 8 queues, to be represented in a long struct

# Call analysis functions
analyze_adaptation_period()
analyze_queues()
analyze_ranks()