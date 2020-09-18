package com.prodigyapps.iutehealthandroid.ui.history

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.prodigyapps.iutehealthandroid.R
import kotlinx.android.synthetic.main.fragment_history.*
import kotlin.properties.Delegates


//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;



class HistoryFragment : Fragment() {

    lateinit var listView : ListView
    lateinit var listView2 : ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_history, container, false)

        listView = root.findViewById(R.id.listView_prescription)
        listView2 = root.findViewById(R.id.listView_refund)

        val mySQLCon = PrescriptionFetchSQLConn(requireContext(), root, listView)
        mySQLCon.execute()

        val mySQLCon2 = RefundFetchSQLConn(requireContext(), root, listView2)
        mySQLCon2.execute()



        return root
    }
}