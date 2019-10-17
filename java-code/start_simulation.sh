#!/bin/bash

# Package project and assemble into .jar
echo "Compiling."
mvn -q -DskipTests package assembly:single

program="java -jar NetBench.jar"

if [ $# -eq 0 ]
  then
    echo "No arguments supplied, running all experiments."
    experiments="$(echo {0..214})"
    analyze=1
  else
    experiments=$@
    analyze=0
fi

num=$(echo "$experiments" | wc -w)

# Run all experiments in the background
current=1
echo "Starting experiments."

for experiment in $experiments; do
    printf "\rStarting experiment %i of %i." $current $num
    current=`expr $current + 1`
    $program $experiment >/dev/null 2>&1 &
done
printf "\rStarted %s experiments, waiting for completion...\n" $num

# Ensure a way out
trap 'kill $(jobs -p) >/dev/null 2>&1' EXIT

# Wait for them to finish
wait

# Run analyze
if [ $analyze -eq 1 ]
  then
    echo "Running analysis."
    $program analyze
fi

echo "Done."
