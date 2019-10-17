load 'projects/sppifo/plots/spectral.pal'
midnight = "#D53E4F"; aqua = "#F46D43"; ocean = "#FDAE61"; wave = "#66C2A5"; stone = "#ABDDA4"; foam = "#3288BD"

set terminal pdfcairo
set term pdfcairo enhanced font "Helvetica,16" size 4in,2.5in
set key opaque

########################################################################################################################
# Mean flow completion time: pFabric-based scheduling schemes
########################################################################################################################
set output 'projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_less_100KB_mean_fct_ms_8.pdf'
set xlabel 'Load'
set xrange [0:6]
set xtics ("0.2" 0, "0.3" 1, "0.4" 2, "0.5" 3, "0.6" 4, "0.7" 5, "0.8" 6)

set ylabel 'Flow Completion Time (ms)'
set yrange [0:10]
set ytics ("0" 0, "2" 2, "4" 4, "6" 6, "8" 8, "10" 10)

plot "projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_less_100KB_mean_fct_ms_8.dat" using 6 title "PIFO" w lp  ls 21 lw 4, \
            '' using 4 title "AFQ"  w lp ls 22 lw 4, \
            '' using 5 title "SP-PIFO"  w lp ls 23 lw 4, \
            '' using 3 title "DCTCP" w lp ls 27 lw 4, \
            '' using 2 title "TCP"  w lp ls 28 lw 4

########################################################################################################################
# Mean flow completion time <100KB: pFabric-based scheduling schemes
########################################################################################################################
set output 'projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_less_100KB_mean_fct_ms_32.pdf'

set xlabel 'Load'
set ylabel 'Flow Completion Time (ms)'
set yrange [0:10]
set ytics ("0" 0, "2" 2, "4" 4, "6" 6, "8" 8, "10" 10)


plot "projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_less_100KB_mean_fct_ms_32.dat" using 6 title "PIFO" w lp  ls 21 lw 4, \
           '' using 4 title "AFQ"  w lp ls 22 lw 4, \
           '' using 5 title "SP-PIFO"  w lp ls 23 lw 4, \
           '' using 3 title "DCTCP" w lp ls 27 lw 4, \
           '' using 2 title "TCP"  w lp ls 28 lw 4


########################################################################################################################
# Different queues SPPIFO
########################################################################################################################
set output 'projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_sppifo_fct_queue_effect.pdf'

set xlabel 'Load'
set ylabel 'Flow Completion Time (ms)'
set yrange [10:50]
set boxwidth 0.8
set style data histogram
set style histogram
set style fill solid 0.6
set bars front
set key top left

set xtics ("0.2" 0, "0.4" 1, "0.6" 2, "0.8" 3)
set ytics ("10" 10, "20" 20, "30" 30, "40" 40, "50" 50)
set xrange [-0.5:3.5]


plot "projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_sppifo_fct_queue_effect.dat" using 2 title "PIFO" linecolor rgb midnight lw 4, \
         '' using 6 title "SP-PIFO Q32" linecolor rgb aqua lw 4, \
         '' using 5 title "SP-PIFO Q24" linecolor rgb ocean lw 4, \
         '' using 4 title "SP-PIFO Q16" linecolor rgb wave lw 4, \
         '' using 3 title "SP-PIFO   Q8" linecolor rgb foam lw 4

########################################################################################################################
# Different queues AFQ
########################################################################################################################
set output 'projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_afq_fct_queue_effect.pdf'

set xlabel 'Load'
set ylabel 'Flow Completion Time (ms)'
set yrange [10:50]
set boxwidth 0.8
set style data histogram
set style histogram
set style fill solid 0.6
set bars front
set key top left

set xtics ("0.2" 0, "0.4" 1, "0.6" 2, "0.8" 3)
set xrange [-0.5:3.5]

plot "projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_afq_fct_queue_effect.dat" using 2 title "PIFO" linecolor rgb midnight lw 4, \
         '' using 6 title "AFQ Q32" linecolor rgb aqua lw 4, \
        '' using 5 title "AFQ Q24" linecolor rgb ocean lw 4, \
         '' using 4 title "AFQ Q16" linecolor rgb wave lw 4, \
         '' using 3 title "AFQ   Q8" linecolor rgb foam lw 4

########################################################################################################################
# Mean flow completion time >10MB: pFabric-based scheduling schemes
########################################################################################################################
set output 'projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_split_mean_fct_ms_32.pdf'

set xlabel 'Flow Size'
set ylabel 'Flow Completion Time (ms)'
set yrange [0.1:10000000]
set logscale y 10
set ytics auto
set key opaque
set key top right
set xtics font "Helvetica,13"
set xrange [-0.5:6.5]
set xtics format ""
set xtics ("{/Symbol \263}2M" 0, "0.2M-1M" 1, "80K" 2, "50K" 3, "30K" 4, "20K" 5, "10K" 6)

set style data histogram
set bars fullwidth
set style histogram errorbars gap 2 lw 3
#set boxwidth 0.7
#set style fill solid 0.3
set bars front

set ytics ("10^1" 10, "10^3" 1000, "10^5" 100000, "10^7" 10000000)

plot "projects/sppifo/plots/sppifo_evaluation/fairness/web_search_workload/fairness_split_mean_fct_ms_32.dat" using 14:15:16 title "PIFO" linecolor rgb midnight lw 3, \
                ''  using 8:9:10 title "AFQ" linecolor rgb aqua lw 3, \
                ''  using 11:12:13 title "SP-PIFO" linecolor rgb ocean lw 3, \
                ''  using 5:6:7  title "DCTCP" linecolor rgb wave lw 3, \
                ''  using 2:3:4 title "TCP" linecolor rgb foam lw 3


