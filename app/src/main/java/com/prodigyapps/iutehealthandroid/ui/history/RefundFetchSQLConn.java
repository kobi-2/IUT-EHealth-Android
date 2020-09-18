package com.prodigyapps.iutehealthandroid.ui.history;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.prodigyapps.iutehealthandroid.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

/*
class PrescriptionFetchData {

     int slipNo;
     String status;
     Bitmap bmp;

    PrescriptionFetchData(){
        slipNo = 0;
        status = "Loading";
        bmp = null;
    }
}
*/

public class RefundFetchSQLConn extends AsyncTask {

    // @SuppressLint to avoid leak
//    @SuppressLint("StaticFieldLeak")
//    private Context context;
//
//    MySQLCon(Context context){
//        this.context = context;
//    }

    private String TAG = "RefundFetchsqlConn";

    private FileInputStream fis;
    private File file;
    private InputStream inputStream;
    private Context context;
    private View root;
    private ListView listView;
//    private ImageView imageViewFetch;

    ArrayList<RefundFetchData> records;


    RefundFetchSQLConn(Context context, View root, ListView listView) throws FileNotFoundException {

        this.context = context;
        this.root = root;
        this.listView = listView;
        records = new ArrayList<RefundFetchData>();

        Log.d(TAG, "RefundFetchSQLConn: constructor called");
    }


    public boolean setupCon() {

        boolean flag = false;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Log.d(TAG, "setupCon: class name done");

            Connection myConn = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12359105", "sql12359105", "XsBjh9d1MD");

            Log.d(TAG, "setupCon: connection setup done");


            String query = "SELECT billNo, status, image, amount from billdatabase where id = ?";
            PreparedStatement pst = myConn.prepareStatement(query);
            //TODO: pass actual id fo student
            pst.setString(1,"170041003");
            ResultSet rs = pst.executeQuery();

            RefundFetchData refundFetchData;
            while (rs.next()) {
                refundFetchData = new RefundFetchData();

                refundFetchData.billNo = rs.getInt("billno");
                refundFetchData.status = rs.getString("status");
                refundFetchData.amount = rs.getString("amount");

                InputStream inputStream = rs.getBinaryStream("image");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                refundFetchData.bmp = BitmapFactory.decodeStream(bufferedInputStream);

                records.add(refundFetchData);
                flag = true;
            }


            pst.close();
            rs.close();

            myConn.close();
            Log.d(TAG, "RefundFetchSQLConn: all connection closed");


        } catch (Exception e) {
            Log.d(TAG, "Exception: error in database: " + e);
        }

        return flag;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        Log.d(TAG, "ImageFetchSQLConn: background starting");

        return setupCon();
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        Log.d(TAG, "onPostExecute: going to parse rs.next()");
        Toast.makeText(context, "Fetching Refund Data", Toast.LENGTH_SHORT).show();

        /*
        ResultSet rs = (ResultSet) o;
        ArrayList<PrescriptionFetchData> records = new ArrayList<PrescriptionFetchData>();
        PrescriptionFetchData prescriptionFetchData = new PrescriptionFetchData();

        try {
            while (rs.next()) {
                //prescriptionFetchData = new PrescriptionFetchData();

                prescriptionFetchData.slipNo = rs.getInt("SlipNo");
                prescriptionFetchData.status = rs.getString("status");

                InputStream inputStream = rs.getBinaryStream("image");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                prescriptionFetchData.bmp = BitmapFactory.decodeStream(bufferedInputStream);

                records.add(prescriptionFetchData);
            }

        }catch (SQLException e) {
            Log.d(TAG, "onPostExecute: error in rs.next()" + e );
            Toast.makeText(context, "Could not fetch prescription", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

         */

        if(o.equals(new Boolean(true))){
            Log.d(TAG, "onPostExecute: going for refund adapter");
            RefundAdapter refundAdapter = new RefundAdapter(context, R.layout.refund_record, records);
            listView.setAdapter(refundAdapter);
        }else {
            Log.d(TAG, "onPostExecute: refund adapter call failed");
        }

    }

}

