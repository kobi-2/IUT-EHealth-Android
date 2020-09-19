package com.prodigyapps.iutehealthandroid.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prodigyapps.iutehealthandroid.R

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    var nameText: TextView? = null
    var idText:TextView? = null
    var deptText:TextView? = null
    var ageText:TextView? = null
    var bgText:TextView? = null
    var contactText:TextView? = null
    var emailText:TextView? = null
    var resText:TextView? = null
    var addressText:TextView? = null

    var profPicture: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        nameText = root.findViewById(R.id.textView_name)
        idText = root.findViewById(R.id.textView_id)
        deptText = root.findViewById(R.id.textView_dept)
        ageText = root.findViewById(R.id.textView_age)
        bgText = root.findViewById(R.id.textView_bg)
        contactText = root.findViewById(R.id.textView_contact)
        emailText = root.findViewById(R.id.textView_email)
        resText = root.findViewById(R.id.textView_res)
        addressText = root.findViewById(R.id.textView_address)

        profPicture = root.findViewById(R.id.imageView_dp)

        Toast.makeText(context, "Fetching User Profile Information", Toast.LENGTH_SHORT).show()
        val profileFetchAsync = ProfileFetchAsync(requireContext(), root, profPicture, nameText, idText, deptText, ageText, bgText, contactText, emailText, resText, addressText)
        profileFetchAsync.execute()

        return root
    }
}