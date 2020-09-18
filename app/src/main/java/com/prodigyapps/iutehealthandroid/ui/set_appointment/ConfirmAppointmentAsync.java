package com.prodigyapps.iutehealthandroid.ui.set_appointment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.prodigyapps.iutehealthandroid.R;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConfirmAppointmentAsync extends AsyncTask {


    private String TAG = "ConfirmAppointmentAsync";

    private Context context;
    View root;
    String problem, day, month, year, time;
    String query;

    ConfirmAppointmentAsync(Context context, View root, String problem, String day, String month, String year, String time) throws FileNotFoundException {

        this.context = context;
        this.root = root;
        this.problem = problem;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;

        query = new String("");

        Log.d(TAG, "constructor called");
        Log.d(TAG, "Appointment problem: " + problem + " time: " + time + " date: " + day + "/" + month + "/" + year + " ");


    }

    public boolean setupCon() {
        boolean uploadStatus = false;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Log.d(TAG, "setupCon: class name done");

            //here sql12357858 in url is database name, 3306 is port number
            Connection myConn = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12359105", "sql12359105", "XsBjh9d1MD");

            Log.d(TAG, "setupCon: connection setup done");


            //TODO: pass actual id...
            String sql = "CALL make_appointment('" + "170041003" + "','" + time+ "','" + problem + "','" + day + "','" + month + "','" + year + "')";
            PreparedStatement pst2 = myConn.prepareStatement(sql);
            pst2.execute();

            query = "Appointment set for " + time + " on " + day + "/" + month + "/" + year + " ";
            Log.d(TAG, "setupCon: appointment successfull");
            uploadStatus = true;

            pst2.close();
            myConn.close();

            Log.d(TAG, " connection closed");



        } catch (Exception e) {
            Log.d(TAG, "Exception: error in database: " + e);
            uploadStatus = false;
        }

        return uploadStatus;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

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

        if(o.equals(new Boolean(true))){

            Toast.makeText(context, query, Toast.LENGTH_SHORT).show();

            Activity activity = (Activity) context;
            Navigation.findNavController(root).navigate(R.id.action_setAppointmentFragment_to_navigation_home);
        }else{
            Toast.makeText(context, "Something happened! Couldn't make appointment", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPostExecute: Error occurred. Couldn't make appointment");
        }

    }


}

