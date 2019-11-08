package com.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseClientWrite {

    public static void main(String[] args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost:2181");
        config.set("zookeeper.znode.parent", "/hbase");

        Connection connection = ConnectionFactory.createConnection(config);
        try {
            Admin admin = connection.getAdmin();
            TableName tableName = TableName.valueOf("myLittleHBaseTable");
            if (!admin.tableExists(tableName)) {
                HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("myLittleFamily");
                hTableDescriptor.addFamily(hColumnDescriptor);
                admin.createTable(hTableDescriptor);
            }

            Table table = connection.getTable(tableName);
            try {

                for (int i = 0; i < 10000; i++) {
                    Put p = new Put(Bytes.toBytes("row" + i));

                    p.add(Bytes.toBytes("myLittleFamily"), Bytes.toBytes("someQualifier"),
                            Bytes.toBytes("Some Value " + i));

                    table.put(p);
                }

            } finally {
                if (table != null) table.close();
            }
        } finally {
            connection.close();
        }
    }
}
