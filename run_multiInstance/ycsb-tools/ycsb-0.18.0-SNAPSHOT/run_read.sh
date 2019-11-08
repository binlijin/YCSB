./bin/ycsb run hbase12 -P workloads/workloadc -p thread=50 -p table=table_128B -p columnfamily=cf -p fieldcount=1 -p recordcount=100000 -p requestdistribution=uniform -p operationcount=100000 -p exportfile=result/result$1.txt -s

