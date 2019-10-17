load 'projects/sppifo/plots/spectral.pal'

set terminal pdfcairo
set term pdfcairo enhanced font "Helvetica,17" size 3.5in,2.5in

set xlabel 'Rank Values'
set ylabel 'Number of Inversions (·10^3)'

set yrange[0:25000]
set ytics ("0" 0, "5" 5000, "10" 10000, "15"15000, "20" 20000, "25" 25000)

set output 'projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/uniform.pdf'
plot "projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/uniform_8_queues/uniform.dat" using 5 title "Fixed Queue Bounds" w l ls 7 lw 6, \
            '' using 4 title "Greedy (optimal)" w l ls 9 lw 6, \
            '' using 3 title "SP-PIFO" w l ls 3 lw 6, \
            '' using 2 title "FIFO" w l ls 8 lw 6