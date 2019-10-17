load 'projects/sppifo/plots/spectral.pal'

set terminal pdfcairo
set term pdfcairo enhanced font "Helvetica,14" size 4.3in,2.5in

########################################################################################################################
# Rank distribution convex
########################################################################################################################
set xlabel 'Rank values' tc ls 11
set ylabel 'Number of packets forwarded' tc ls 11
set xrange [0:100]
set yrange [0:60000]
set output 'projects/sppifo/plots/greedy_convergence/greedy_convex_ranks.pdf'
set xtics ("0" 0, "10" 10, "20" 20, "30" 30, "40" 40, "50" 50, "60" 60, "70" 70, "80" 80, "90" 90, "100" 100)

set style data histogram
set style histogram cluster gap 1
set style fill solid
set boxwidth 0.9

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_convex_ranks.dat" using 2 title "Queue0" ls 1 lw 4, \
            '' using 3 title "Queue1" ls 2 lw 4, \
            '' using 4 title "Queue2" ls 3 lw 4, \
            '' using 5 title "Queue3" ls 4 lw 4, \
            '' using 6 title "Queue4" ls 5 lw 4, \
            '' using 7 title "Queue5" ls 6 lw 4, \
            '' using 8 title "Queue6" ls 7 lw 4, \
            '' using 9 title "Queue7" ls 8 lw 4

########################################################################################################################
# Queue-bounds evolution convex
########################################################################################################################
set xlabel 'Packet arrival time' tc ls 11
set ylabel 'Queue levels' tc ls 11
set xrange [0:800000]
set yrange [0:100]
set output 'projects/sppifo/plots/greedy_convergence/greedy_convex_queuebounds.pdf'
set xtics ("0" 0, "200000" 200000, "400000" 400000, "600000" 600000, "800000" 800000)

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_convex_queuebounds.dat" using 2 title "Queue0" w l ls 1 lw 4, \
            '' using 3 title "Queue1" w l ls 2 lw 4, \
            '' using 4 title "Queue2" w l ls 3 lw 4, \
            '' using 5 title "Queue3" w l ls 4 lw 4, \
            '' using 6 title "Queue4" w l ls 5 lw 4, \
            '' using 7 title "Queue5" w l ls 6 lw 4, \
            '' using 8 title "Queue6" w l ls 7 lw 4, \
            '' using 9 title "Queue7" w l ls 8 lw 4

########################################################################################################################
# Rank distribution exponential
########################################################################################################################
set xlabel 'Rank values' tc ls 11
set ylabel 'Number of packets forwarded' tc ls 11
set xrange [0:100]
set yrange [0:60000]
set output 'projects/sppifo/plots/greedy_convergence/greedy_exponential_ranks.pdf'
set xtics ("0" 0, "10" 10, "20" 20, "30" 30, "40" 40, "50" 50, "60" 60, "70" 70, "80" 80, "90" 90, "100" 100)

set style data histogram
set style histogram cluster gap 1
set style fill solid
set boxwidth 0.9

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_exponential_ranks.dat" using 2 title "Queue0" ls 1 lw 4, \
            '' using 3 title "Queue1" ls 2 lw 4, \
            '' using 4 title "Queue2" ls 3 lw 4, \
            '' using 5 title "Queue3" ls 4 lw 4, \
            '' using 6 title "Queue4" ls 5 lw 4, \
            '' using 7 title "Queue5" ls 6 lw 4, \
            '' using 8 title "Queue6" ls 7 lw 4, \
            '' using 9 title "Queue7" ls 8 lw 4

########################################################################################################################
# Queue-bounds evolution exponential
########################################################################################################################
set xlabel 'Packet arrival time' tc ls 11
set ylabel 'Queue levels' tc ls 11
set xrange [0:800000]
set yrange [0:100]
set output 'projects/sppifo/plots/greedy_convergence/greedy_exponential_queuebounds.pdf'
set xtics ("0" 0, "200000" 200000, "400000" 400000, "600000" 600000, "800000" 800000)

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_exponential_queuebounds.dat" using 2 title "Queue0" w l ls 1 lw 4, \
            '' using 3 title "Queue1" w l ls 2 lw 4, \
            '' using 4 title "Queue2" w l ls 3 lw 4, \
            '' using 5 title "Queue3" w l ls 4 lw 4, \
            '' using 6 title "Queue4" w l ls 5 lw 4, \
            '' using 7 title "Queue5" w l ls 6 lw 4, \
            '' using 8 title "Queue6" w l ls 7 lw 4, \
            '' using 9 title "Queue7" w l ls 8 lw 4

########################################################################################################################
# Rank distribution inverseexponential
########################################################################################################################
set xlabel 'Rank values' tc ls 11
set ylabel 'Number of packets forwarded' tc ls 11
set xrange [0:100]
set yrange [0:50000]
set output 'projects/sppifo/plots/greedy_convergence/greedy_inverse_exponential_ranks.pdf'
set xtics ("0" 0, "10" 10, "20" 20, "30" 30, "40" 40, "50" 50, "60" 60, "70" 70, "80" 80, "90" 90, "100" 100)

set style data histogram
set style histogram cluster gap 1
set style fill solid
set boxwidth 0.9

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_inverse_exponential_ranks.dat" using 2 title "Queue0" ls 1 lw 4, \
            '' using 3 title "Queue1" ls 2 lw 4, \
            '' using 4 title "Queue2" ls 3 lw 4, \
            '' using 5 title "Queue3" ls 4 lw 4, \
            '' using 6 title "Queue4" ls 5 lw 4, \
            '' using 7 title "Queue5" ls 6 lw 4, \
            '' using 8 title "Queue6" ls 7 lw 4, \
            '' using 9 title "Queue7" ls 8 lw 4

########################################################################################################################
# Queue-bounds evolution inverse exponential
########################################################################################################################
set xlabel 'Packet arrivals' tc ls 11
set ylabel 'Queue bounds' tc ls 11
set xrange [0:800000]
set yrange [0:100]
set output 'projects/sppifo/plots/greedy_convergence/greedy_inverse_exponential_queuebounds.pdf'
set xtics ("0" 0, "200k" 200000, "400k" 400000, "600k" 600000, "800k" 800000)

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_inverse_exponential_queuebounds.dat" using 2 title "Queue0" w l ls 1 lw 4, \
            '' using 3 title "Queue1" w l ls 2 lw 4, \
            '' using 4 title "Queue2" w l ls 3 lw 4, \
            '' using 5 title "Queue3" w l ls 4 lw 4, \
            '' using 6 title "Queue4" w l ls 5 lw 4, \
            '' using 7 title "Queue5" w l ls 6 lw 4, \
            '' using 8 title "Queue6" w l ls 7 lw 4, \
            '' using 9 title "Queue7" w l ls 8 lw 4


########################################################################################################################
# Rank distribution minmax
########################################################################################################################
set xlabel 'Rank values' tc ls 11
set ylabel 'Number of packets forwarded' tc ls 11
set xrange [0:50]
set yrange [0:60000]
set output 'projects/sppifo/plots/greedy_convergence/greedy_minmax_ranks.pdf'
set xtics ("0" 0, "10" 10, "20" 20, "30" 30, "40" 40, "50" 50, "60" 60, "70" 70, "80" 80, "90" 90, "100" 100)

set style data histogram
set style histogram cluster gap 1
set style fill solid
set boxwidth 0.9

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_minmax_ranks.dat" using 2 title "Queue0" ls 1 lw 4, \
            '' using 3 title "Queue1" ls 2 lw 4, \
            '' using 4 title "Queue2" ls 3 lw 4, \
            '' using 5 title "Queue3" ls 4 lw 4, \
            '' using 6 title "Queue4" ls 5 lw 4, \
            '' using 7 title "Queue5" ls 6 lw 4, \
            '' using 8 title "Queue6" ls 7 lw 4, \
            '' using 9 title "Queue7" ls 8 lw 4

########################################################################################################################
# Queue-bounds evolution minmax
########################################################################################################################
set xlabel 'Packet arrival time' tc ls 11
set ylabel 'Queue levels' tc ls 11
set xrange [0:800000]
set yrange [0:100]
set output 'projects/sppifo/plots/greedy_convergence/greedy_minmax_queuebounds.pdf'
set xtics ("0" 0, "200000" 200000, "400000" 400000, "600000" 600000, "800000" 800000)

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_minmax_queuebounds.dat" using 2 title "Queue0" w l ls 1 lw 4, \
            '' using 3 title "Queue1" w l ls 2 lw 4, \
            '' using 4 title "Queue2" w l ls 3 lw 4, \
            '' using 5 title "Queue3" w l ls 4 lw 4, \
            '' using 6 title "Queue4" w l ls 5 lw 4, \
            '' using 7 title "Queue5" w l ls 6 lw 4, \
            '' using 8 title "Queue6" w l ls 7 lw 4, \
            '' using 9 title "Queue7" w l ls 8 lw 4


########################################################################################################################
# Rank distribution poisson
########################################################################################################################
set xlabel 'Rank values' tc ls 11
set ylabel 'Number of packets forwarded' tc ls 11
set xrange [0:100]
set yrange [0:50000]
set output 'projects/sppifo/plots/greedy_convergence/greedy_poisson_ranks.pdf'
set xtics ("0" 0, "10" 10, "20" 20, "30" 30, "40" 40, "50" 50, "60" 60, "70" 70, "80" 80, "90" 90, "100" 100)

set style data histogram
set style histogram cluster gap 1
set style fill solid
set boxwidth 0.9

set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_poisson_ranks.dat" using 2 title "Queue0" ls 1 lw 4, \
            '' using 3 title "Queue1" ls 2 lw 4, \
            '' using 4 title "Queue2" ls 3 lw 4, \
            '' using 5 title "Queue3" ls 4 lw 4, \
            '' using 6 title "Queue4" ls 5 lw 4, \
            '' using 7 title "Queue5" ls 6 lw 4, \
            '' using 8 title "Queue6" ls 7 lw 4, \
            '' using 9 title "Queue7" ls 8 lw 4

########################################################################################################################
# Queue-bounds evolution poisson
########################################################################################################################
set xlabel 'Packet arrivals' tc ls 11
set ylabel 'Queue bounds' tc ls 11
set xrange [0:800000]
set yrange [0:100]
set output 'projects/sppifo/plots/greedy_convergence/greedy_poisson_queuebounds.pdf'
set xtics ("0" 0, "200k" 200000, "400k" 400000, "600k" 600000, "800k" 800000)
set key horizontal tc ls 11
set key outside
set key bot center

plot "projects/sppifo/plots/greedy_convergence/greedy_poisson_queuebounds.dat" using 2 title "Queue0" w l ls 1 lw 4, \
            '' using 3 title "Queue1" w l ls 2 lw 4, \
            '' using 4 title "Queue2" w l ls 3 lw 4, \
            '' using 5 title "Queue3" w l ls 4 lw 4, \
            '' using 6 title "Queue4" w l ls 5 lw 4, \
            '' using 7 title "Queue5" w l ls 6 lw 4, \
            '' using 8 title "Queue6" w l ls 7 lw 4, \
            '' using 9 title "Queue7" w l ls 8 lw 4

 ########################################################################################################################
 # Rank distribution uniform
 ########################################################################################################################
 set term pdfcairo enhanced font "Helvetica,14" size 4.3in,2in
 set xlabel 'Rank values' tc ls 11
 set ylabel 'Number of packets' tc ls 11
 set xrange [0:100]
 set yrange [0:20000]
 set output 'projects/sppifo/plots/greedy_convergence/greedy_uniform_ranks.pdf'
 set xtics ("0" 0, "10" 10, "20" 20, "30" 30, "40" 40, "50" 50, "60" 60, "70" 70, "80" 80, "90" 90, "100" 100)

 set style data histogram
 set style histogram cluster gap 1
 set style fill solid
 set boxwidth 0.9

 set key horizontal tc ls 11
 set key outside
 unset key

 plot "projects/sppifo/plots/greedy_convergence/greedy_uniform_ranks.dat" using 2 title "Queue0" ls 1 lw 4, \
             '' using 3 title "Queue1" ls 2 lw 4, \
             '' using 4 title "Queue2" ls 3 lw 4, \
             '' using 5 title "Queue3" ls 4 lw 4, \
             '' using 6 title "Queue4" ls 5 lw 4, \
             '' using 7 title "Queue5" ls 6 lw 4, \
             '' using 8 title "Queue6" ls 7 lw 4, \
             '' using 9 title "Queue7" ls 8 lw 4

 ########################################################################################################################
 # Queue-bounds evolution uniform
 ########################################################################################################################
 set term pdfcairo enhanced font "Helvetica,14" size 4.3in,2.5in
 set xlabel 'Packet arrivals' tc ls 11
 set ylabel 'Queue bounds' tc ls 11
 set xrange [0:800000]
 set yrange [0:100]
 set output 'projects/sppifo/plots/greedy_convergence/greedy_uniform_queuebounds.pdf'
set xtics ("0" 0, "200k" 200000, "400k" 400000, "600k" 600000, "800k" 800000)
 set key horizontal tc ls 11
 set key outside
 set key bot center

 plot "projects/sppifo/plots/greedy_convergence/greedy_uniform_queuebounds.dat" using 2 title "Queue0" w l ls 1 lw 4, \
             '' using 3 title "Queue1" w l ls 2 lw 4, \
             '' using 4 title "Queue2" w l ls 3 lw 4, \
             '' using 5 title "Queue3" w l ls 4 lw 4, \
             '' using 6 title "Queue4" w l ls 5 lw 4, \
             '' using 7 title "Queue5" w l ls 6 lw 4, \
             '' using 8 title "Queue6" w l ls 7 lw 4, \
             '' using 9 title "Queue7" w l ls 8 lw 4
