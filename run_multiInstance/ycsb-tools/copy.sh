#! /usr/bin/env bash                                                                                                                                                                                            
bin=`dirname "$0"`
bin=`cd "$bin">/dev/null; pwd`
echo $bin

HOSTLIST=$bin/hosts

for host in `cat "$HOSTLIST"`; do
  scp -r $bin $host:$bin
done

