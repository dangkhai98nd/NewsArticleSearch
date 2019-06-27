package com.example.newsarticlesearch

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_options.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.os.Build
import android.support.annotation.RequiresApi
import java.time.Year
import java.util.*
import android.icu.util.Calendar as Calendar1


class OptionsDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.layout_options,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arrayAdapter = ArrayAdapter<String>(context,R.layout.spinner_item,resources.getStringArray(R.array.sort_order))
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        spinnerSortOrder.adapter = arrayAdapter
        Toast.makeText(context,spinnerSortOrder.selectedItem.toString(),Toast.LENGTH_SHORT).show()

        ivBeginDate.setOnClickListener {
            val c = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    edtBeginDate.setText(
                        year.toString() + (monthOfYear + 1) + dayOfMonth.toString()
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        ivEndDate.setOnClickListener{
            val c = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    edtEndDate.setText(
                        year.toString() + (monthOfYear + 1) + dayOfMonth.toString()
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

    }


}