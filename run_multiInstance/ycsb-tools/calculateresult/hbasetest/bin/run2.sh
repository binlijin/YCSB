
for f in /Users/lijinbin/workspace/hbaseShadeTest/lib/*.jar
do
JAR_CLASSPATH=$JAR_CLASSPATH:$f
done
export JAR_CLASSPATH

java -classpath $JAR_CLASSPATH  com.hbase.test.MyLittleHBaseClient


