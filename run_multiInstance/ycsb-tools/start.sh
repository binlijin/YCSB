#! /usr/bin/env bash                                                                                                                                                                                            
bin=`dirname "$0"`
bin=`cd "$bin">/dev/null; pwd`
echo $bin

HOSTLIST=$bin/hosts

remote_cmd="cd /data/hbaseadmin/ycsb/ycsb-tools; sh run_mutliread.sh $1"

for host in `cat "$HOSTLIST"`; do
  ssh $host $remote_cmd 2>&1
done

