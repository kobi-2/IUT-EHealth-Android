package com.prodigyapps.iutehealthandroid.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import androidx.annotation.Nullable;

import java.sql.*;
import java.io.*;

public class ImageRefundUploadSQLConn extends AsyncTask {

    // @SuppressLint to avoid leak
//    @SuppressLint("StaticFieldLeak")
//    private Context context;
//
//    MySQLCon(Context context){
//        this.context = context;
//    }

    private String TAG = "ImageUploadSQLConn";

    private FileInputStream fis;
    private File file;
    private Context context;
    private InputStream inputStream;

    ImageRefundUploadSQLConn(Context context, @Nullable Uri uri) throws FileNotFoundException {

        this.context = context;

        Log.d(TAG, "ImageUploadSQLConn: constructor called");
        Log.d(TAG, "ImageUploadSQLConn: in the constructor Uri: "+ uri);

        inputStream =  context.getContentResolver().openInputStream(uri);


        /*
       // probably don't need these

        if( uri != null ) {
           Log.d(TAG, "ImageUploadSQLConn: Uri starting");
           this.file = new File(String.valueOf(uri));
       }else{
           Log.d(TAG, "ImageUploadSQLConn: Uri couldn't found or converted to File");
       }

         */

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



            String query = "SELECT MAX(BillNo) from billdatabase";
            PreparedStatement pst = myConn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int last_billNo = rs.getInt("MAX(BillNo)");
            rs.close();


            Log.d(TAG, "ImageUploadSQLConn: last_billNo: " + last_billNo);


            String query2 = "INSERT into billdatabase (BillNo,id,image,amount) values (?,?,?,?)";
            pst = myConn.prepareStatement(query2);
            /*
            String query2 = "INSERT into billdatabase (BillNo,id,image) values (?,?,?)";
            pst = myConn.prepareStatement(query2);
            */


           /*
           // I Don't think we need this

            try {
//                file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                fis = new FileInputStream( file);
//                fis = new FileInputStream(String.valueOf(inputStream));
            } catch (FileNotFoundException e) {

                Log.d(TAG, "setupCon: file error!! " + e);
            }

            */

            pst.setString(1,Integer.toString(last_billNo+1));
            //TODO: put actual user id
            pst.setString(2,"170041003");
//            pst.setBinaryStream(3,(InputStream)fis,(int)file.length());
            pst.setBinaryStream(3,inputStream,inputStream.available());
            pst.setString(4,"0");
            pst.execute();

            Log.d(TAG, "ImageUploadSQLConn: ended");


            myConn.close();

            Log.d(TAG, "ImageUploadSQLConn: connection closed");

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
        Toast.makeText(context, "Uploading Image", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(o.equals(new Boolean(true))){
            Toast.makeText(context, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
        }else if(o.equals(new Boolean(false))){
            Toast.makeText(context, "Image Uploaded Failed. Please, Try Again!", Toast.LENGTH_SHORT).show();
        }

    }
}

