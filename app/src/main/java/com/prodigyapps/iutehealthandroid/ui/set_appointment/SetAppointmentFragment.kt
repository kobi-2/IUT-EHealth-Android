package com.prodigyapps.iutehealthandroid.ui.set_appointment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.prodigyapps.iutehealthandroid.R
import kotlinx.android.synthetic.main.fragment_set_appointment.*
import java.util.*


class SetAppointmentFragment : Fragment() {


    private lateinit var radioGroup: RadioGroup
//    private lateinit var radioLayoutParams: RadioGroup.LayoutParams


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_set_appointment, container, false)

        root.findViewById<ImageButton>(R.id.imageButton_DatePicker).setOnClickListener{
            setDatePicker()
        }

        radioGroup = root.findViewById(R.id.radioButtonGroup)
        addRadioButtons()

        root.findViewById<Button>(R.id.cancel_button).setOnClickListener { activity!!.onBackPressed() }


        return root;
    }


    fun setDatePicker(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

//        Toast.makeText(requireContext(), "year $year month: $month day: $day ", Toast.LENGTH_SHORT).show()

//            create and show dialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay->

                    /**
                     * check if previous date is selected
                     */
                    if((mYear<year) || (mYear==year && mMonth<month)|| (mYear==year && mMonth==month && mDay<day))
                    {
                        Toast.makeText(
                            requireContext(),
                            "Please provide a valid date!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{

//                        TODO sql conneciton  call addRadioButton() from here
                        val s : String = "$mDay / $mMonth / $mYear"
                        textView_date.text = s


                    }

                }, year, month, day)

            datePickerDialog.show()

    }


    private fun addRadioButtons() {

//        TODO: Integrate Data from Database

        var radioButton : RadioButton
        for (i in 1..20){
            radioButton = RadioButton(requireContext())
            radioButton.text = i.toString();
            val radioLayoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT)
            radioGroup.addView(radioButton, radioLayoutParams)
        }

    }



}