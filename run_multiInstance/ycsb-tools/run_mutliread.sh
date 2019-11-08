#! /usr/bin/env bash                                                                                                                                                                                            
bin=`dirname "$0"`
bin=`cd "$bin">/dev/null; pwd`
echo $bin

HOSTLIST=$bin/hosts

num=$1
echo $num


cd $bin/ycsb-0.18.0-SNAPSHOT/

mkdir -p log
mkdir -p result

for ((i=1; i<=$num; i++));
do 
  #echo $i
  nohup ./run_read.sh $i >log/$i.txt 2>&1 &
done

