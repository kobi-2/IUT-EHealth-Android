package com.prodigyapps.iutehealthandroid.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;


class ImageFetchData{
    boolean imageFetchedSuccessfully;
    Bitmap bmp;
    ImageFetchData(){
        imageFetchedSuccessfully = false;
        bmp = null;
    }

    /*
    ImageFetchData(boolean b){
        imageFetchedSuccessfully = b;
    }
     */
}


public class ImageFetchSQLConn extends AsyncTask {

    // @SuppressLint to avoid leak
//    @SuppressLint("StaticFieldLeak")
//    private Context context;
//
//    MySQLCon(Context context){
//        this.context = context;
//    }

    private String TAG = "ImageFetchsqlConn";

    private FileInputStream fis;
    private File file;
    private Context context;
    private View root;
    private ImageFetchData imageFetchData;
    private ImageView imageViewFetch;

    ImageFetchSQLConn(Context context, View root , ImageView imageView) throws FileNotFoundException {

        this.context = context;
        this.root = root;
        this.imageViewFetch = imageView;
        //imageViewFetch.setImageResource(R.drawable.popeye);

        imageFetchData = new ImageFetchData();

        Log.d(TAG, "ImageFetchsqlConn: constructor called");
//        Log.d(TAG, "ImagefetchsqlConn: in the constructor Uri: "+ uri);
//        inputStream =  context.getContentResolver().openInputStream(uri);


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
    private InputStream inputStream;

    public ImageFetchData setupCon() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Log.d(TAG, "setupCon: class name done");

            //here sql12357858 in url is database name, 3306 is port number
            Connection myConn = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12359105", "sql12359105", "XsBjh9d1MD");

            Log.d(TAG, "setupCon: connection setup done");


            String query = "SELECT BillNo,image from billdatabase WHERE billNo = ? AND id=? ";
            PreparedStatement pst = myConn.prepareStatement(query);
            //TODO: set actual bill no, and id...
//            pst.setString(1,BillNumber);
//            pst.setString(2,userSession.getUsername());
            pst.setString(1,"1");
            pst.setString(2,"170041003");

            ResultSet rs = pst.executeQuery();

            Log.d(TAG, "ImageFetchSQLConn: image fetching query executed. now moving onto extracting the value to inputstream ");

            InputStream inputStream = null;
            // TODO: refactor as to fit mine...
            if(rs.next()){
                Log.d(TAG, "image fetch...query has result!");

                inputStream = rs.getBinaryStream("image");

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
//                imageViewFetch.setImageBitmap(bmp);

                imageFetchData.imageFetchedSuccessfully = true;
                imageFetchData.bmp = bmp;

                Log.d(TAG, "ImageFetchSQLConn: done setting image to imageview after fetching");


                /*
                OutputStream os = new FileOutputStream(new File("refundImage.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while((size = is.read(content))!=-1){
                    os.write(content,0,size);
                }
                is.close();
                os.close();
                image2 = new Image("file:refundImage.jpg",400,500,true,true);
                refundImage.setImage(image2);
                refundImage.setFitHeight(300);
                refundImage.setFitWidth(400);
                refundImage.setPreserveRatio(true);
                */

            }else {
                Log.d(TAG, "ImageFetchSQLConn: image fetched from database FAILED!!");
            }

            pst.close();
            rs.close();

            Log.d(TAG, "ImageFetchSQLConn: image fetched from database");


            myConn.close();
            Log.d(TAG, "ImageFetchSQLConn: connection closed");


        } catch (Exception e) {
            Log.d(TAG, "Exception: error in database: " + e);
        }

        return imageFetchData;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        Log.d(TAG, "ImageFetchSQLConn: background starting");

        return setupCon();
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        ImageFetchData imageFetchData = (ImageFetchData) o;

        if(imageFetchData.imageFetchedSuccessfully){

            Toast.makeText(context, "Image Collected Successfully", Toast.LENGTH_SHORT).show();
            imageViewFetch.setImageBitmap(imageFetchData.bmp);

        }else if(!imageFetchData.imageFetchedSuccessfully){
            Toast.makeText(context, "Image Collection Failed. Please, Try Again!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "An Error Occurred [ImageFetchSQLConn.java onPostExecute()]", Toast.LENGTH_SHORT).show();
        }

    }

}

