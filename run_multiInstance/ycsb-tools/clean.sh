#! /usr/bin/env bash                                                                                                                                                                                            
bin=`dirname "$0"`
bin=`cd "$bin">/dev/null; pwd`
echo $bin

HOSTLIST=$bin/hosts

remote_cmd="rm -rf /data/hbaseadmin/ycsb"
for host in `cat "$HOSTLIST"`; do
  ssh $host $remote_cmd 2>&1
done

remote_cmd="mkdir -p /data/hbaseadmin/ycsb"
for host in `cat "$HOSTLIST"`; do
  ssh $host $remote_cmd 2>&1
done

