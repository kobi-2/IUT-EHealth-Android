package com.prodigyapps.iutehealthandroid.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

public class CheckAppointmentAsync extends AsyncTask {

    // @SuppressLint to avoid leak
//    @SuppressLint("StaticFieldLeak")
//    private Context context;
//
//    MySQLCon(Context context){
//        this.context = context;
//    }

    private String TAG = "CheckAppointmentAsync";

    private Context context;
    private View root;
    private TextView textView;
    String query, nextAppMsg;

    CheckAppointmentAsync(Context context, View root, TextView textView) throws FileNotFoundException {

        this.context = context;
        this.root = root;
        this.textView = textView;

        query = "";
        nextAppMsg = "";

        Log.d(TAG, " constructor called");


    }

    public boolean setupCon() {
        boolean uploadStatus = false;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Log.d(TAG, "setupCon: class name done");

            Connection myConn = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12359105", "sql12359105", "XsBjh9d1MD");

            Log.d(TAG, "setupCon: connection setup done");


            query = "SELECT st_id,time,problem,day,month,year from appointment where st_id = ? order by year desc, month desc, day asc, time asc";
            PreparedStatement pst = myConn.prepareStatement(query);
            //TODO: pass actual id...
            pst.setString(1,"170041003");
            ResultSet rs = pst.executeQuery();

            String day="",month="",year="", time="", problem = "";
            if(rs.next()){
                day = rs.getString("day");
                month = rs.getString("month");
                year = rs.getString("year");
                time = rs.getString("time");
                problem = rs.getString("problem");
            }

            nextAppMsg = "Next Appointment: " + time + " on " + day + "/" + month + "/" + year + " \n For " + problem;
            Log.d(TAG, "setupCon: appointment fetch successfull");
            uploadStatus = true;

            rs.close();
            pst.close();
            myConn.close();

            Log.d(TAG, "connection closed");

            uploadStatus = true;

        } catch (Exception e) {
            Log.d(TAG, "Exception: error in database: " + e);
            uploadStatus = false;
        }

        return uploadStatus;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        Log.d(TAG, "ImageUploadSQLConn: background starting");

        boolean uploadStatus = setupCon();

        return uploadStatus;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(o.equals(new Boolean(true))){
            textView.setText(nextAppMsg);
        }

    }
}

