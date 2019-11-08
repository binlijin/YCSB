package com.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseClientScan {

    public static void main(String[] args) throws IOException {
        int argindex = 0;
        int num = 0;
        while (args[argindex].startsWith("-")) {
            if (args[argindex].compareToIgnoreCase("-num") == 0) {
                argindex++;
                if (argindex >= args.length) {
                    System.out.println("Missing argument value for -threads.");
                    System.exit(0);
                }
                num = Integer.parseInt(args[argindex]);
                argindex++;
                System.out.println("num=" + num);
            }
            if (argindex >= args.length) {
                break;
            }
        }

        Configuration config = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(config);
        try {
            TableName tableName = TableName.valueOf("ycsb_result");
            String family = "cf";
            String qualifier = "throughput";

            Table table = connection.getTable(tableName);
            try {
                Scan scan = new Scan();
                scan.setReversed(true);
                ResultScanner resultScanner = table.getScanner(scan);
                double totalThroughput = 0;
                int count = 0;
                for (Result result : resultScanner) {
                    Cell cell =
                        result.getColumnLatestCell(Bytes.toBytes(family), Bytes.toBytes(qualifier));
                    if (cell != null) {
                        double throughput = Bytes.toDouble(CellUtil.cloneValue(cell));
                        totalThroughput += throughput;
                        count++;
                        System.out.println(
                            count + ", row=" + Bytes.toString(CellUtil.cloneRow(cell))
                                + ", throughput=" + throughput);
                        if (count >= num) {
                            break;
                        }
                    }
                }
                System.out.println("total throughput=" + totalThroughput);
            } finally {
                if (table != null) {
                    table.close();
                }
            }
        } finally {
            connection.close();
        }
    }
}
