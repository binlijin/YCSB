package com.hbase.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class hbasetest {

  public static void main(String[] args) throws IOException {
    DateFormat dateTimeformat = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
    String strBeginDate = dateTimeformat.format(new Date());
    System.out.println(strBeginDate);
  }

}
