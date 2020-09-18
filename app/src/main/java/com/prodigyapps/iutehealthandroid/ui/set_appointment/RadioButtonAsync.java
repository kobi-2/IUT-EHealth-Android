package com.prodigyapps.iutehealthandroid.ui.set_appointment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RadioButtonAsync extends AsyncTask {


    private String TAG = "RadioButtonAsync";

    private Context context;
    private RadioGroup radioGroup;
    private RadioGroup.LayoutParams layoutParams;
    private RadioButton radioButton;
    int day, month, year;

    RadioButtonAsync(Context context, RadioGroup radioGroup, RadioGroup.LayoutParams layoutParams, int day, int month, int year) throws FileNotFoundException {

        this.context = context;
        this.radioGroup = radioGroup;
        this.layoutParams = layoutParams;
        this.day = day;
        this.month = month;
        this.year = year;

        radioButton = new RadioButton(context);

        Log.d(TAG, "ImageUploadSQLConn: constructor called");


    }

    public ArrayList<String> setupCon() {
        boolean uploadStatus = false;
        ArrayList<String> s = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Log.d(TAG, "setupCon: class name done");

            //here sql12357858 in url is database name, 3306 is port number
            Connection myConn = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12359105", "sql12359105", "XsBjh9d1MD");

            Log.d(TAG, "setupCon: connection setup done");



            String query = "SELECT times from allTimes WHERE times not in (select time from appointment where day = ? and month = ? and year = ?) order by times";
            Log.d(TAG, "setupCon: query written");
            PreparedStatement pst = myConn.prepareStatement(query);
            Log.d(TAG, "setupCon: query prepared");
            pst.setString(1, String.valueOf(day));
            pst.setString(2, String.valueOf(month));
            pst.setString(3, String.valueOf(year));
            ResultSet rs = pst.executeQuery();
            Log.d(TAG, "setupCon: query executed");

//            while(rs.next()){
//                radioButton = new RadioButton(context);
//                radioButton.setText(rs.getString("times"));
//                radioGroup.addView(radioButton, layoutParams);
//            }


            while(rs.next()){
                s.add(rs.getString("times"));
            }

            rs.close();
            pst.close();
            myConn.close();

            Log.d(TAG, " connection closed");

            uploadStatus = true;

        } catch (Exception e) {
            Log.d(TAG, "Exception: error in database: " + e);
            uploadStatus = false;
        }

        return s;
    }


    @Override
    protected ArrayList<String> doInBackground(Object[] objects) {

        Log.d(TAG, " background starting");

        return setupCon();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        ArrayList<String> s = (ArrayList<String>) o;

            for(int i =0; i< s.size(); i++){
                radioButton = new RadioButton(context);
                radioButton.setText(s.get(i));
                radioGroup.addView(radioButton, layoutParams);
            }
    }
}

