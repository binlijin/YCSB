package com.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class MyLittleHBaseClient {

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
                Put p = new Put(Bytes.toBytes("myLittleRow"));

                p.add(Bytes.toBytes("myLittleFamily"), Bytes.toBytes("someQualifier"),
                        Bytes.toBytes("Some Value"));

                table.put(p);

                Get g = new Get(Bytes.toBytes("myLittleRow"));
                Result r = table.get(g);
                byte [] value = r.getValue(Bytes.toBytes("myLittleFamily"),
                        Bytes.toBytes("someQualifier"));

                String valueStr = Bytes.toString(value);
                System.out.println("GET: " + valueStr);

                Scan s = new Scan();
                s.addColumn(Bytes.toBytes("myLittleFamily"), Bytes.toBytes("someQualifier"));
                ResultScanner scanner = table.getScanner(s);
                try {
                    for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                        System.out.println("Found row: " + rr);
                    }

                } finally {
                    scanner.close();
                }

            } finally {
                if (table != null) table.close();
            }
        } finally {
            connection.close();
        }
    }
}
