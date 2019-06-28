package com.example.newsarticlesearch

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_options.*
import java.util.*


class OptionsDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.layout_options, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arrayAdapter =
            ArrayAdapter<String>(context, R.layout.spinner_item, resources.getStringArray(R.array.sort_order))
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerSortOrder.adapter = arrayAdapter



        ivBeginDate.setOnClickListener {
            val c = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    edtBeginDate.setText(
                        year.toString() + (monthOfYear + 1) + dayOfMonth.toString()
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        ivEndDate.setOnClickListener {
            val c = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    edtEndDate.setText(
                        year.toString() + (monthOfYear + 1) + dayOfMonth.toString()
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        btnSave.setOnClickListener{
            this.dismiss()
        }

    }


}