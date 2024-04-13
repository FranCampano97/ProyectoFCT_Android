package com.example.proyectofct

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.proyectofct.ui.view.Fragment.DetallesFragment
import com.example.proyectofct.ui.view.Fragment.EnergiaFragment
import com.example.proyectofct.ui.view.Fragment.InstalacionFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InstalacionFragment()
            1 -> EnergiaFragment()
            2 -> DetallesFragment()
            else -> InstalacionFragment()
        }
    }
}