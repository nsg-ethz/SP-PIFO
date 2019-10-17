import csv
import sys
import os
import random
import math as m

def analyze_inversions(distribution):
    inversionsFIFO = {}
    inversionsSPPIFO = {}
    inversionsGreedy = {}
    totalFIFO = 0
    totalSPPIFO = 0
    totalGreedy = 0

    for i in range(0,101):
        inversionsFIFO[i] = 0
        inversionsSPPIFO[i] = 0
        inversionsGreedy[i] = 0

    with open("temp/sppifo/sppifo_analysis/alternative_distributions/" + distribution + "/FIFO/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsFIFO[rank] = inversionsFIFO[rank] + 1
                totalFIFO = totalFIFO + 1

    with open("temp/sppifo/sppifo_analysis/alternative_distributions/" + distribution + "/SPPIFO/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsSPPIFO[rank] = inversionsSPPIFO[rank] + 1
                totalSPPIFO = totalSPPIFO + 1

    with open("temp/sppifo/sppifo_analysis/alternative_distributions/" + distribution + "/Greedy/inversions_tracking.csv.log") as file:
        reader = csv.reader(file)
        for row in reader:
            port = int(row[0])
            rank = int(row[1])
            if port == 1:
                inversionsGreedy[rank] = inversionsGreedy[rank] + 1
                totalGreedy = totalGreedy + 1

    # Write results in file
    w = open("projects/sppifo/plots/sppifo_analysis/alternative_distributions/" + distribution + "/" + distribution + ".dat", 'w')
    axis = range(0,100)
    w.write("#  FIFO    SPPIFO  Greedy\n")
    for line in range(0,len(axis)):
        w.write("%s   %s   %s   %s\n" % (axis[line], inversionsFIFO[line], inversionsSPPIFO[line], inversionsGreedy[line]))

# Call analysis functions
analyze_inversions('convex')
analyze_inversions('exponential')
analyze_inversions('poisson')
analyze_inversions('inverse_exponential')