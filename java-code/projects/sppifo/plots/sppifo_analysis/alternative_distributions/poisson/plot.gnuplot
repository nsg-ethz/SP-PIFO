load 'projects/sppifo/plots/spectral.pal'

set terminal pdfcairo
set term pdfcairo enhanced font "Helvetica,17" size 3.5in,2.5in

set xlabel 'Rank Values'
set ylabel 'Number of Inversions (·10^3)'

set yrange[0:70000]
set ytics ("0" 0, "10" 10000, "20" 20000, "30" 30000, "40" 40000, "50" 50000, "60" 60000, "70" 70000)

set output 'projects/sppifo/plots/sppifo_analysis/alternative_distributions/poisson/poisson.pdf'
plot "projects/sppifo/plots/sppifo_analysis/alternative_distributions/poisson/poisson.dat" using 4 title "Greedy (optimal)" w l ls 9 lw 6, \
          '' using 3 title "SP-PIFO" w l ls 3 lw 6, \
          '' using 2 title "FIFO" w l ls 8 lw 6
