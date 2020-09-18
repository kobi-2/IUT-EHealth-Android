package com.prodigyapps.iutehealthandroid.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.prodigyapps.iutehealthandroid.R


const val TAG = "HomeFragment"


class HomeFragment : Fragment() {

    private lateinit var root : View

    var imageViewUpload: ImageView? = null
    var imageViewFetch: ImageView? = null
    private val PICK_IMAGE = 1
    var imageUri: Uri? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)


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

        root.findViewById<Button>(R.id.textView_Reason).setOnClickListener{
            Log.d(TAG, "onCreateView: SetAppointmentButton Pressed")

            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_setAppointmentFragment)
        }

        imageViewUpload = root.findViewById(R.id.imageView_upload)
//        imageViewUpload?.setImageResource(R.drawable.popeye)

        imageViewFetch = root.findViewById(R.id.imageView_fetch)
        //imageViewFetch?.setImageResource(R.drawable.popeye)

        root.findViewById<Button>(R.id.button_refund).setOnClickListener {
            Log.d(TAG, "onCreateView: Refund Button Pressed")

            openGallery()
        }

        root.findViewById<Button>(R.id.button_prescription).setOnClickListener {
            Log.d(TAG, "onCreateView: Prescription Button Pressed")

            fetchPrescriptionImage()
        }

        return root

    }



    private fun openGallery() {
        val gallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data != null) {
                imageUri = data.data
            }
            imageViewUpload!!.setImageURI(imageUri)

//            imageUri?.path

            Log.d(TAG, "onActivityResult: imageUri: $imageUri")
            Log.d(TAG, "onActivityResult: imgeUri path: ${imageUri?.path}")


            /*

            // probably don't need these

            val uriPathHelper = URIPathHelper()
            val filePath = imageUri?.let { uriPathHelper.getPath(requireContext(), it) }

            val bitmap =
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)

            val inputStream = imageUri?.let { requireContext().contentResolver.openInputStream(it) }

             */


            if( imageUri != null){
                Log.d(TAG, "onCreate: ImageUploadSQLConn starting")
                val mySQLCon = ImageUploadSQLConn(requireContext(), imageUri)
//                val mySQLCon = ImageUploadSQLConn(requireContext(), bitmap)
                mySQLCon.execute()
                imageViewUpload!!.setImageURI(imageUri)
            }
            Log.d(TAG, "onCreate: ImageUploadSQLConn finished")

        }

    }


    private fun fetchPrescriptionImage(){
        //TODO: Probably dont need to pass imageuri
        val mySQLCon = ImageFetchSQLConn(requireContext(), root, imageViewFetch)
        mySQLCon.execute()
        //TODO: <code> imageViewFetch!!.setImageURI(imageUri)
    }


}