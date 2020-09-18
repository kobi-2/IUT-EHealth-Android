package com.prodigyapps.iutehealthandroid.ui.set_appointment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.mysql.jdbc.PreparedStatement
import com.prodigyapps.iutehealthandroid.R
import kotlinx.android.synthetic.main.fragment_set_appointment.*
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*
import kotlin.properties.Delegates


class SetAppointmentFragment : Fragment() {


    var appDay by Delegates.notNull<Int>()
    var appMonth by Delegates.notNull<Int>()
    var appYear by Delegates.notNull<Int>()


    private lateinit var radioGroup: RadioGroup
    private lateinit var radioLayoutParams: RadioGroup.LayoutParams



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
        //addRadioButtons()
         radioLayoutParams = RadioGroup.LayoutParams(
             RadioGroup.LayoutParams.MATCH_PARENT,
             RadioGroup.LayoutParams.MATCH_PARENT
         )

        root.findViewById<Button>(R.id.cancel_button).setOnClickListener { activity!!.onBackPressed() }
        root.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            var problem : String = root.findViewById<EditText>(R.id.editText_reason_for_appointment).text.toString()

            val selectedId = radioGroup.checkedRadioButtonId
            val radioButton: RadioButton = root.findViewById(selectedId) as RadioButton
            val time : String = radioButton.text.toString()

            val confirmAppointmentAsync = ConfirmAppointmentAsync(requireContext(), root, problem, appDay.toString(), appMonth.toString(), appYear.toString(), time);
            confirmAppointmentAsync.execute()
        }


        return root;
    }


    fun setDatePicker(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

//        Toast.makeText(requireContext(), "year $year month: $month day: $day ", Toast.LENGTH_SHORT).show()

            var flag : Boolean = false
//            create and show dialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->

                    /**
                     * check if previous date is selected
                     */
                    if ((mYear < year) || (mYear == year && mMonth < month) || (mYear == year && mMonth == month && mDay < day)) {
                        Toast.makeText(
                            requireContext(),
                            "Please provide a valid date!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

//                        TODO sql conneciton  call addRadioButton() from here

                        appDay = mDay
                        appMonth = mMonth + 1
                        appYear = mYear
                        val s: String = "$appDay / $appMonth / $appYear"
                        textView_nextAppointment.text = s

                        radioGroup.removeAllViews()
                        flag = true
//                        AddRButtons(requireContext(), radioGroup, radioLayoutParams, appDay, appMonth, appYear).execute()
//                        addRadioButtons()
                        var radioButtonAsync = RadioButtonAsync(
                            requireContext(),
                            radioGroup,
                            radioLayoutParams,
                            appDay,
                            appMonth,
                            appYear
                        )
                        radioButtonAsync.execute()
                    }

                }, year, month, day
            )

            datePickerDialog.show()

            if(flag){
                //addRadioButtons()
                //AddRButtons(requireContext(), radioGroup, radioLayoutParams, appDay, appMonth, appYear).execute()
            }
    }


    private fun addRadioButtons() {

//        TODO: Integrate Data from Database

        var radioButton : RadioButton
        for (i in 1..20){
            radioButton = RadioButton(requireContext())
            radioButton.text = i.toString();
//            val radioLayoutParams = RadioGroup.LayoutParams(
//                RadioGroup.LayoutParams.MATCH_PARENT,
//                RadioGroup.LayoutParams.MATCH_PARENT
//            )
            radioGroup.addView(radioButton, radioLayoutParams)
        }

    }



    companion object {
        private class AddRButtons(
            mContext: Context,
            mRadioGroup: RadioGroup,
            mRadioLayoutParams: RadioGroup.LayoutParams,
            mDay: Int,
            mMonth: Int,
            mYear: Int
        ) :
            AsyncTask<String, Void, String>() {

            private val TAG = "addRButtons"

            var context: Context by Delegates.notNull()
            var radioGroup: RadioGroup by Delegates.notNull()
            lateinit var radioButton : RadioButton
            var radioLayoutParams : RadioGroup.LayoutParams by Delegates.notNull()
            var day : Int
            var month : Int
            var year : Int

            init {
                context = mContext
                radioGroup = mRadioGroup
                radioButton = RadioButton(mContext)
                radioLayoutParams = mRadioLayoutParams

                day = mDay
                month = mMonth
                year = mYear
            }


            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
            }


            @SuppressLint("WrongThread")
            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG, "doInBackground: starts")

                var flag = "false"

                Class.forName("com.mysql.jdbc.Driver").newInstance()

                Log.d(TAG, "setupCon: class name done")

                val myConn = DriverManager.getConnection(
                    "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12359105",
                    "sql12359105",
                    "XsBjh9d1MD"
                )

                Log.d(TAG, "setupCon: connection setup done")

                val query = "SELECT times from allTimes WHERE times not in (select time from appointment where day = ? and month = ? and year = ?) order by times"
                val pst: PreparedStatement = myConn.prepareStatement(query) as PreparedStatement
                pst.setString(1, day.toString())
                pst.setString(2, month.toString())
                pst.setString(3, year.toString())

                var rs: ResultSet = pst.executeQuery()

                Log.d(TAG, "doInBackground: query done")

                while(rs.next()){
                    radioButton = RadioButton(context)
                    radioButton.text = rs.getString("times")
                    radioGroup.addView(radioButton, radioLayoutParams)
                    flag = "true"
                }

                pst.close()
                rs.close()
                myConn.close()

                return flag
            }

        }
    }


}