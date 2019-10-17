# Plot statistics
# Mean global flow completion time vs. utilization pFabric
lambdasdata = [4000, 6000, 10000, 15000, 22500, 37000, 60000]
lambdasweb = [3600, 5200, 7000, 8900, 11100, 14150, 19000]
row = 0

for x in lambdasweb:
    file = "../temp_save/albert/pFabric/web_search_workload/"+str(x)+"/SPPIFO8_pFabric/analysis/flow_completion.statistics"
    r = open(file, 'r')
    lines = r.readlines()
    for i, line in enumerate(lines):
        if "less_100KB_99th_fct_ms" in line:
            print(line.split("=")[1].split("\n")[0])
            break
    r.close()