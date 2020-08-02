package com.prodigyapps.iutehealthandroid.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.prodigyapps.iutehealthandroid.R


const val TAG = "HomeFragement"


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)



        root.findViewById<Button>(R.id.buttonZoomCall).setOnClickListener { view ->
            Log.d(TAG, "onCreateView: Zoom Button Pressed")
            Toast.makeText(context, "Starting Zoom Call", Toast.LENGTH_SHORT).show()

//            Snackbar.make(view, "Starting Zoom Call", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

//            this is meeting link...probably unique
//            val meetingLink: String = "https://zoom.us/j/98182761298?pwd=SmtHU2FEZHA2a0h0T1BValYweEZRZz09"
//            this is using personal id
//            val meetingLink: String = "https://bdren.zoom.us/s/9016922344"
//            this is personal meeting link
            val meetingLink: String = "https://zoom.us/j/9016922344?pwd=YXNnZGwwRHQyMVZoSnpWcnVLWEpQdz09"
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(meetingLink))

            startActivity(browserIntent)
        }

        root.findViewById<Button>(R.id.buttonSetAppointment).setOnClickListener{
            Log.d(TAG, "onCreateView: SetAppointmentButton Pressed")

            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_setAppointmentFragment)
        }


        return root


    }
}