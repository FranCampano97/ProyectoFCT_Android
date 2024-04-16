package com.example.proyectofct.ui.view.Fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.proyectofct.R
import java.util.Calendar

class DatePickerFragment(val listener: (day: Int, month: Int, year: Int) -> Unit) :
    DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val fechaActual = Calendar.getInstance()
        val day = fechaActual.get(Calendar.DAY_OF_MONTH)
        val month = fechaActual.get(Calendar.MONTH)
        val year = fechaActual.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context, this, year, month, day)
        return picker
    }

}