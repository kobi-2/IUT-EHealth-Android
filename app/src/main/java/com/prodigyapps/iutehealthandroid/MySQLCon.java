package com.prodigyapps.iutehealthandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLCon extends AsyncTask {

    // @SuppressLint to avoid leak
//    @SuppressLint("StaticFieldLeak")
//    private Context context;
//
//    MySQLCon(Context context){
//        this.context = context;
//    }


    public void setupCon() {
        String TAG = "MysqlCon";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.d(TAG, "setupCon: class name done");

            //here sql12357858 in url is database name, 3306 is port number
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12359105", "sql12359105", "XsBjh9d1MD");
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12353692?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=ConvertToNull&serverTimezone=GMT", "sql12353692", "NruRn74dY6");

//            Toast.makeText(context, "Successful Database Connection", Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(context, "Error in database: " + e, Toast.LENGTH_SHORT).show();
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

