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


class ViewHolder2(v: View){
    val billNo: TextView = v.findViewById(R.id.textView_billNo)
    val status: TextView = v.findViewById(R.id.textView_status_2)
    val amount: TextView = v.findViewById(R.id.textView_amount)
    val refundImage: ImageView = v.findViewById(R.id.imageView_refund)
}


internal class RefundAdapter(context: Context, private val resource: Int, private val applications: List<RefundFetchData>) : ArrayAdapter<RefundFetchData>(context, resource, applications) {

    private val TAG = "RefundAdapter"
    private val inflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getView: called")
        val viewHolder: ViewHolder2

        val view: View
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder2(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder2
        }

        val current = applications[position]

        viewHolder.billNo.text = current.billNo.toString()
        viewHolder.status.text = current.status
        viewHolder.amount.text = current.amount
        viewHolder.refundImage.setImageBitmap(current.bmp)


        return view
    }


    override fun getCount(): Int {
        Log.d(TAG, "getCount: called")
        return applications.size
    }

}