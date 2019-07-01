package com.example.newsarticlesearch

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.dialog_options.*
import java.text.SimpleDateFormat
import java.util.*


class OptionsDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.dialog_options, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val bundle: Bundle? = arguments
        val queryHashMap = bundle?.getSerializable("queryHashMapOptions") as HashMap<String, String>

        val arrayAdapter =
            ArrayAdapter<String>(view.context, R.layout.spinner_item, resources.getStringArray(R.array.sort_order))
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerSortOrder.adapter = arrayAdapter

        edtBeginDate.setText(queryHashMap["begin_date"])
        edtEndDate.setText(queryHashMap["end_date"])

        if (queryHashMap["sort"] == "newest") {
            spinnerSortOrder.setSelection(2)
        } else if (queryHashMap["sort"] != null) {
            spinnerSortOrder.setSelection(1)
        }

        val strNewsDeskValue: String? = queryHashMap["fq"]
        if (strNewsDeskValue != null) {
            if (strNewsDeskValue.contains("Arts")) {
                cbArts.isChecked = true
            }
            if (strNewsDeskValue.contains("Sports")) {
                cbSport.isChecked = true
            }
            if (strNewsDeskValue.contains("Fashion") && strNewsDeskValue.contains("Style")) {
                cbFashionStyle.isChecked = true
            }

        }

        ivBeginDate.setOnClickListener {
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    c.set(year, month, dayOfMonth)
                    edtBeginDate.setText(SimpleDateFormat("yyyyMMdd").format(c.time).toString())
                }, mYear, mMonth, mDay
            )

            datePickerDialog.show()
        }

        ivEndDate.setOnClickListener {
            val c = Calendar.getInstance()

            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    c.set(year, month, dayOfMonth)
                    edtEndDate.setText(SimpleDateFormat("yyyyMMdd").format(c.time).toString())
                }, mYear, mMonth, mDay
            )

            datePickerDialog.show()
        }

        btnSave.setOnClickListener {

            if (edtBeginDate.text.toString() != "") {
                queryHashMap["begin_date"] = edtBeginDate.text.toString()
            } else {
                queryHashMap.remove("begin_date")
            }
            if (edtEndDate.text.toString() != "") {
                queryHashMap["end_date"] = edtEndDate.text.toString()
            } else {
                queryHashMap.remove("end_date")
            }

            val strSort = spinnerSortOrder.selectedItem.toString()
            if (strSort.isEmpty()) {
                queryHashMap.remove("sort")
            } else {
                queryHashMap["sort"] = strSort
            }

            var strNewsDesk = "news_desk:("

            if (cbArts.isChecked) {
                strNewsDesk += "\"Arts\" "
            }
            if (cbFashionStyle.isChecked) {
                strNewsDesk += "\"Fashion\" \"Style\" "
            }
            if (cbSport.isChecked) {
                strNewsDesk += "\"Sports\" "
            }
            strNewsDesk += ")"

            if (strNewsDesk == "news_desk:()") {
                queryHashMap.remove("news_desk")
            } else {
                queryHashMap["fq"] = strNewsDesk
            }

            mOnSendData?.sendData(queryHashMap)
            this.dismiss()
        }

        btnCancel.setOnClickListener{
            this.dismiss()
        }

        btnClear.setOnClickListener{
            edtEndDate.setText("")
            edtBeginDate.setText("")
            spinnerSortOrder.setSelection(0)
            cbArts.isChecked = false
            cbFashionStyle.isChecked = false
            cbSport.isChecked = false
        }

    }


    interface OnSendData {
        fun sendData(queryMap: HashMap<String, String>)

    }

    var mOnSendData: OnSendData? = null


    override fun onAttach(context: Context?) {

        super.onAttach(context)

        try {
            mOnSendData = activity as OnSendData
        } catch (e: Exception) {

        }
    }


}