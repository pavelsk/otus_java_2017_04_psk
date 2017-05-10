#!/bin/bash

GC_PARAMS=(
    "-XX:+UseSerialGC"
    "-XX:+UseParallelGC"
    "-XX:+UseParallelOldGC"
    "-XX:+UseConcMarkSweepGC -XX:-UseParNewGC"
    "-XX:+UseConcMarkSweepGC -XX:+UseParNewGC"
    "-XX:+UseG1GC"
)

printf "Build package...\n\n"

JARSTRING=$(mvn clean package | grep "Building jar:" | tail -1)
JAR=$(echo $JARSTRING | sed -E 's/\[INFO\] Building jar: (.*)/\1/')

# Размер массива объектов
SIZE=$((1024*1024*2))
# Время измерения работы GC
DURATION=5000


SUMMARY=( 0 "")

for gc in "${GC_PARAMS[@]}"; do
    echo "Run with '${gc}'"
    java -Xms512m -Xms512m $gc -jar $JAR $SIZE $DURATION
done