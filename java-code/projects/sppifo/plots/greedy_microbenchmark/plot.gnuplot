load 'projects/sppifo/plots/spectral.pal'

set terminal pdfcairo
set term pdfcairo enhanced font "Helvetica,15" size 4in,2.5in

set output 'projects/sppifo/plots/greedy_microbenchmark/unpifoness_adaptation_period.pdf'
set xlabel 'Iteration Number'
set ylabel 'Expected Unpifoness (·10^6)'
set key opaque

set yrange[0:8000000]
set ytics ("0" 0, "2" 2000000, "4" 4000000, "6" 6000000, "8" 8000000)

plot "projects/sppifo/plots/greedy_microbenchmark/unpifoness_adaptation_period.dat" using 2 title "k = 50" w l ls 1 lw 4, \
            '' using 3 title "k = 250" w l ls 2 lw 4, \
            '' using 4 title "k = 500" w l ls 3 lw 4, \
            '' using 5 title "k = 1000" w l ls 4 lw 4, \
            '' using 6 title "k = 2500" w l ls 5 lw 4, \
            '' using 7 title "k = 5000" w l ls 6 lw 4, \
            '' using 8 title "k = 7000" w l ls 7 lw 4

##################################################################################################################

set output 'projects/sppifo/plots/greedy_microbenchmark/unpifoness_queues.pdf'
set xlabel 'Iteration Number'
set ylabel 'Expected Unpifoness (·10^5)'
set key opaque

set yrange[0:1000000]
set ytics ("0" 0, "2" 200000, "4" 400000, "6" 600000, "8" 800000, "10" 1000000)

plot "projects/sppifo/plots/greedy_microbenchmark/unpifoness_queues.dat" using 2 title "Q = 8" w l ls 2 lw 4, \
            '' using 3 title "Q = 12" w l ls 3 lw 4, \
            '' using 4 title "Q = 16" w l ls 4 lw 4, \
            '' using 5 title "Q = 20" w l ls 5 lw 4, \
            '' using 6 title "Q = 24" w l ls 6 lw 4, \
            '' using 7 title "Q = 28" w l ls 7 lw 4, \
            '' using 8 title "Q = 32" w l ls 8 lw 4

##################################################################################################################

set output 'projects/sppifo/plots/greedy_microbenchmark/unpifoness_ranks.pdf'
set xlabel 'Iteration Number'
set ylabel 'Expected Unpifoness (·10^7)'
set key opaque

set yrange[0:120000000]
set ytics ("0" 0, "2" 2000000000, "4" 40000000, "6" 60000000, "8" 80000000, "10" 100000000, "12" 120000000)

plot "projects/sppifo/plots/greedy_microbenchmark/unpifoness_ranks.dat" using 2 title "R = 100" w l ls 1 lw 4, \
            '' using 3 title "R = 200" w l ls 2 lw 4, \
            '' using 4 title "R = 400" w l ls 3 lw 4, \
            '' using 5 title "R = 600" w l ls 4 lw 4, \
            '' using 6 title "R = 800" w l ls 7 lw 4, \
            '' using 7 title "R = 1000" w l ls 8 lw 4, \