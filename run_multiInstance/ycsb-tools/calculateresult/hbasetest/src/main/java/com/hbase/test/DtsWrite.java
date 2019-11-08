package com.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Date;

public class DtsWrite {

    public static void main(String[] args) throws IOException {
        long num = 1000000;
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.println("args" + i + " = " + args[i]);
                if (args[i].equalsIgnoreCase("num")) {
                    num = Long.valueOf(args[i + 1]);
                    i++;
                }
            }
        }

        System.out.println("num = " + num);

        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "hbasedev.com:2181");
        config.set("zookeeper.znode.parent", "/hbase");

        Connection connection = ConnectionFactory.createConnection(config);
        try {
            Admin admin = connection.getAdmin();
            TableName tableName = TableName.valueOf("record");
            if (!admin.tableExists(tableName)) {
                HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("data");
                hTableDescriptor.addFamily(hColumnDescriptor);
                admin.createTable(hTableDescriptor);
            }

            Table table = connection.getTable(tableName);
            long start = System.currentTimeMillis();
            try {
                for (int i = 0; i < num; i++) {
                    Put p = new Put(Bytes.toBytes("row" + i));
                    p.add(Bytes.toBytes("data"), Bytes.toBytes("someQualifier"),
                            Bytes.toBytes("Some Value " + i));
                    table.put(p);
                    if (i > 0 && (i % 10000 == 0)) {
                        System.out.println(new Date() + " write " + i + " record.");
                    }
                }
            } finally {
                if (table != null) table.close();
            }
            System.out.println("Takes " + (System.currentTimeMillis() - start) / 1000 + " s.");
        } finally {
            connection.close();
        }
    }
}
