package com.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Date;

public class HBaseClientGet {

    public static void main(String[] args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost:2181");
        config.set("zookeeper.znode.parent", "/hbase");
        config.set("hbase.client.start.log.errors.counter", "-1");
        config.set("hbase.ipc.client.connection.minIdleTimeBeforeClose", "20000");
        config.set("hbase.background.check.meta.enable", "true");

        Connection connection = ConnectionFactory.createConnection(config);
        try {
            TableName tableName = TableName.valueOf("myLittleHBaseTable");

            Table table = connection.getTable(tableName);
            try {

                for (int i = 0; i < 10000; i++) {
                    for (int j = 0; j < 10000; j++) {
                        Get get = new Get(Bytes.toBytes("row" + j));
                        table.get(get);
                    }
//                    try {
//                        System.out.println("sleep " + i + " 1");
//                        Thread.sleep(20000);
//                        System.out.println("current1 " + new Date());
//                    } catch (Exception e) {
//                    }
//                    try {
//                        System.out.println("sleep " + i + " 2");
//                        Thread.sleep(20000);
//                        System.out.println("current2 " + new Date());
//                    } catch (Exception e) {
//                    }
                }

            } finally {
                if (table != null) table.close();
            }
        } finally {
            connection.close();
        }
    }
}
