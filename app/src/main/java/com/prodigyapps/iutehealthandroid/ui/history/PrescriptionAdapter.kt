package com.prodigyapps.iutehealthandroid.ui.history

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.prodigyapps.iutehealthandroid.R


class ViewHolder(v: View){
    val slipNo: TextView = v.findViewById(R.id.textView_slipNo)
    val status: TextView = v.findViewById(R.id.textView_status)
    val prescriptionImage: ImageView = v.findViewById(R.id.imageView_prescription)
}


internal class PrescriptionAdapter(context: Context, private val resource: Int, private val applications: List<PrescriptionFetchData>) : ArrayAdapter<PrescriptionFetchData>(context, resource, applications) {

    private val TAG = "PrescriptionAdapter"
    private val inflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getView: called")
        val viewHolder: ViewHolder

        val view: View
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val current = applications[position]

        viewHolder.slipNo.text = current.slipNo.toString()
        viewHolder.status.text = current.status
        viewHolder.prescriptionImage.setImageBitmap(current.bmp)


        return view
    }


    override fun getCount(): Int {
        Log.d(TAG, "getCount: called")
        return applications.size
    }

}