load 'projects/sppifo/plots/spectral.pal'

set terminal pdfcairo
set term pdfcairo enhanced font "Helvetica,17" size 3.5in,2.5in

set xlabel 'Utilization (%)'
set ylabel 'Number of Inversions (·10^5)'

set yrange[0:5000000]
set ytics ("0" 0, "10" 1000000, "20" 2000000, "30" 3000000, "40" 4000000, "50" 5000000)

set xrange[20:90]

set output 'projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/utilization/utilization.pdf'
plot "projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/utilization/utilization.dat" using 1:4 title "Greedy (optimal)" w lp ls 29 lw 6, \
             '' using 1:3 title "SP-PIFO" w lp ls 23 lw 6, \
             '' using 1:2 title "FIFO" w lp ls 28 lw 6