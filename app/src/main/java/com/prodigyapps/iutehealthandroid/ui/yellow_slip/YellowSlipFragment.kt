package com.prodigyapps.iutehealthandroid.ui.yellow_slip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.prodigyapps.iutehealthandroid.R

class YellowSlipFragment : Fragment() {

    private lateinit var yellowSlipViewModel: YellowSlipViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        yellowSlipViewModel =
                ViewModelProviders.of(this).get(YellowSlipViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_yellow_slip, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        yellowSlipViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}