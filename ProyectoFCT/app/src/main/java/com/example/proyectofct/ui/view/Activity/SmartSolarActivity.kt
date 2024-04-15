package com.example.proyectofct.ui.view.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.proyectofct.R
import com.example.proyectofct.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmartSolarActivity : AppCompatActivity() {
    private lateinit var adaptador: FragmentStateAdapter
    private lateinit var tablayout: TabLayout
    private lateinit var viewpager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_solar)
        tablayout = findViewById(R.id.tablayout)
        viewpager = findViewById(R.id.viewpager)
        adaptador = ViewPagerAdapter(this)
        viewpager.adapter = adaptador

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "Mi instalación"
                1 -> "Energía"
                2 -> "Detalles"
                else -> "Mi instalación"
            }
        }.attach()
    }
}
