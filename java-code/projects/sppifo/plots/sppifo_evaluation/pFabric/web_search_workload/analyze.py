#!/usr/bin/python

# This python scripts extracts the data from the logs that we want to plot and outputs it in a format that gnuplot can
# later on represent.

# Theoretical plot number of combinations
#!/usr/bin/python
import math

if __name__ == '__main__':

########################################################################################################################

    # Mean global flow completion time vs. utilization pFabric
    lambdas = [3600, 5200, 7000, 8900, 11100, 14150, 19000]

    FCTs = [[0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0]]
    row = 0

    for x in lambdas:
        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/TCP/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_99th_fct_ms" in line:
                FCTs[row][0]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/DCTCP/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_99th_fct_ms" in line:
                FCTs[row][1]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/PIFO/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_99th_fct_ms" in line:
                FCTs[row][2]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/SPPIFO/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_99th_fct_ms" in line:
                FCTs[row][3]=line.split("=")[1].split("\n")[0]
                break
        r.close()
        row = row + 1

    w = open('projects/sppifo/plots/sppifo_evaluation/pFabric/web_search_workload/pFabric_less_100KB_99th_fct_ms.dat', 'w')
    w.write("#    TCP    DCTCP    PIFO    SPPIFO\n")
    w.write("3600   %s    %s    %s    %s    \n" % (FCTs[0][0], FCTs[0][1], FCTs[0][2], FCTs[0][3]))
    w.write("5200   %s    %s    %s    %s    \n" % (FCTs[1][0], FCTs[1][1], FCTs[1][2], FCTs[1][3]))
    w.write("7000   %s    %s    %s    %s    \n" % (FCTs[2][0], FCTs[2][1], FCTs[2][2], FCTs[2][3]))
    w.write("8900   %s    %s    %s    %s    \n" % (FCTs[3][0], FCTs[3][1], FCTs[3][2], FCTs[3][3]))
    w.write("11100   %s    %s    %s    %s    \n" % (FCTs[4][0], FCTs[4][1], FCTs[4][2], FCTs[4][3]))
    w.write("14150   %s    %s    %s    %s    \n" % (FCTs[5][0], FCTs[5][1], FCTs[5][2], FCTs[5][3]))
    w.write("19000   %s    %s    %s    %s    \n" % (FCTs[6][0], FCTs[6][1], FCTs[6][2], FCTs[6][3]))
    w.close()


########################################################################################################################

    # Mean global flow completion time vs. utilization pFabric
    lambdas = [3600, 5200, 7000, 8900, 11100, 14150, 19000]

    FCTs = [[0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0]]
    row = 0

    for x in lambdas:
        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/TCP/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_mean_fct_ms" in line:
                FCTs[row][0]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/DCTCP/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_mean_fct_ms" in line:
                FCTs[row][1]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/PIFO/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_mean_fct_ms" in line:
                FCTs[row][2]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/SPPIFO/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "less_100KB_mean_fct_ms" in line:
                FCTs[row][3]=line.split("=")[1].split("\n")[0]
                break
        r.close()
        row = row + 1

    w = open('projects/sppifo/plots/sppifo_evaluation/pFabric/web_search_workload/pFabric_less_100KB_mean_fct_ms.dat', 'w')
    w.write("#    TCP    DCTCP    PIFO    SPPIFO\n")
    w.write("3600   %s    %s    %s    %s    \n" % (FCTs[0][0], FCTs[0][1], FCTs[0][2], FCTs[0][3]))
    w.write("5200   %s    %s    %s    %s    \n" % (FCTs[1][0], FCTs[1][1], FCTs[1][2], FCTs[1][3]))
    w.write("7000   %s    %s    %s    %s    \n" % (FCTs[2][0], FCTs[2][1], FCTs[2][2], FCTs[2][3]))
    w.write("8900   %s    %s    %s    %s    \n" % (FCTs[3][0], FCTs[3][1], FCTs[3][2], FCTs[3][3]))
    w.write("11100   %s    %s    %s    %s    \n" % (FCTs[4][0], FCTs[4][1], FCTs[4][2], FCTs[4][3]))
    w.write("14150   %s    %s    %s    %s    \n" % (FCTs[5][0], FCTs[5][1], FCTs[5][2], FCTs[5][3]))
    w.write("19000   %s    %s    %s    %s    \n" % (FCTs[6][0], FCTs[6][1], FCTs[6][2], FCTs[6][3]))
    w.close()

########################################################################################################################

    # Mean global flow completion time vs. utilization pFabric
    lambdas = [3600, 5200, 7000, 8900, 11100, 14150, 19000]

    FCTs = [[0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [0,0,0,0]]
    row = 0

    for x in lambdas:
        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/TCP/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "geq_1MB_mean_fct_ms" in line:
                FCTs[row][0]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/DCTCP/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "geq_1MB_mean_fct_ms" in line:
                FCTs[row][1]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/PIFO/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "geq_1MB_mean_fct_ms" in line:
                FCTs[row][2]=line.split("=")[1].split("\n")[0]
                break
        r.close()

        file = "temp/sppifo/sppifo_evaluation/pFabric/web_search_workload/"+str(x)+"/SPPIFO/analysis/flow_completion.statistics"
        r = open(file, 'r')
        lines = r.readlines()
        for i, line in enumerate(lines):
            if "geq_1MB_mean_fct_ms" in line:
                FCTs[row][3]=line.split("=")[1].split("\n")[0]
                break
        r.close()
        row = row + 1

    w = open('projects/sppifo/plots/sppifo_evaluation/pFabric/web_search_workload/pFabric_geq_1MB_mean_fct_ms.dat', 'w')
    w.write("#    TCP    DCTCP    PIFO    SPPIFO\n")
    w.write("3600   %s    %s    %s    %s    \n" % (FCTs[0][0], FCTs[0][1], FCTs[0][2], FCTs[0][3]))
    w.write("5200   %s    %s    %s    %s    \n" % (FCTs[1][0], FCTs[1][1], FCTs[1][2], FCTs[1][3]))
    w.write("7000   %s    %s    %s    %s    \n" % (FCTs[2][0], FCTs[2][1], FCTs[2][2], FCTs[2][3]))
    w.write("8900   %s    %s    %s    %s    \n" % (FCTs[3][0], FCTs[3][1], FCTs[3][2], FCTs[3][3]))
    w.write("11100   %s    %s    %s    %s    \n" % (FCTs[4][0], FCTs[4][1], FCTs[4][2], FCTs[4][3]))
    w.write("14150   %s    %s    %s    %s    \n" % (FCTs[5][0], FCTs[5][1], FCTs[5][2], FCTs[5][3]))
    w.write("19000   %s    %s    %s    %s    \n" % (FCTs[6][0], FCTs[6][1], FCTs[6][2], FCTs[6][3]))
    w.close()