package com.prodigyapps.iutehealthandroid;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLCon extends AsyncTask {

    public void setupCon() {
        String TAG = "MysqlCon";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.d(TAG, "setupCon: class name done");

            //here sql12353692 in url is database name, 3306 is port number
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12353692", "sql12353692", "NruRn74dY6");
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12353692?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=ConvertToNull&serverTimezone=GMT", "sql12353692", "NruRn74dY6");
            Log.d(TAG, "setupCon: connection setup done");

            Statement stmt = con.createStatement();
            Log.d(TAG, "setupCon: create statement performed");
            ResultSet rs = stmt.executeQuery("select * from test");
            Log.d(TAG, "setupCon: statement execute query performed");

            while (rs.next()) {
                Log.d(TAG, "database: 1: " + rs.getInt(1));
                Log.d(TAG, "database: 2: " + rs.getString(2));
                Log.d(TAG, "database: 3: " + rs.getString(3));
//                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }

            con.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception: error in database: " + e);
//            System.out.println("error in database: ");
//            System.out.println(e);}
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        setupCon();
        return null;
    }
}
