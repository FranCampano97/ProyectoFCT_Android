package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityFacturaListBinding
import com.example.proyectofct.databinding.ActivityFiltrarFacturasBinding
import com.example.proyectofct.ui.view.Fragment.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltrarFacturasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFiltrarFacturasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiltrarFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelar.setOnClickListener {
            finish()
        }


        binding.btnDesde.setOnClickListener {
            showPickerDialog(binding.btnDesde)
        }
        binding.btnHasta.setOnClickListener {
            showPickerDialog(binding.btnHasta)
        }
    }


    private fun showPickerDialog(button: Button) {
        val datePicker =
            DatePickerFragment { day, month, year ->
                onDateSelected(day, month, year, button)
            }

        datePicker.show(supportFragmentManager, "datePicker")
    }


    private fun onDateSelected(day: Int, month: Int, year: Int, button: Button) {
        button.setText("$day-$month-$year")
    }

}