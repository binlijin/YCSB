CLASSPATH="/data/hbaseadmin/ycsb/ycsb-tools/getresult/conf/"
for f in /data/hbaseadmin/ycsb/ycsb-tools/getresult/lib/*.jar
do
 CLASSPATH=$CLASSPATH:$f
done
export CLASSPATH

java -classpath $CLASSPATH com.hbase.test.HBaseClientScan $@

