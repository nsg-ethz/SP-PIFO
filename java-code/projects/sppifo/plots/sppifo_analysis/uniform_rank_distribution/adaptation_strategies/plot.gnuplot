load 'projects/sppifo/plots/spectral.pal'

set terminal pdfcairo
set term pdfcairo enhanced font "Helvetica,17" size 3.5in,2.5in

set xlabel 'Rank Values'
set ylabel 'Number of Inversions (·10^3)'

set yrange[0:25000]
set ytics ("0" 0, "5" 5000, "10" 10000, "15"15000, "20" 20000, "25" 25000)

set output 'projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/adaptation_strategies.pdf'
plot "projects/sppifo/plots/sppifo_analysis/uniform_rank_distribution/adaptation_strategies/adaptation_strategies.dat" using 4 title "SP-PIFO Queue Bound" w l ls 9 lw 4, \
            '' using 3 title "SP-PIFO Cost" w l ls 3 lw 4, \
            '' using 5 title "SP-PIFO Rank" w l ls 2 lw 4, \
            '' using 2 title "SP-PIFO 1" w l ls 8 lw 4