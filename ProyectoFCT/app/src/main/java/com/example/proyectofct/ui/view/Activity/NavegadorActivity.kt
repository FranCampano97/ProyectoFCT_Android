package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofct.databinding.ActivityNavegadorBinding

class NavegadorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavegadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavegadorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnExterno.setOnClickListener {
            binding.webviewBien.visibility = View.GONE
            val url = "https://www.iberdrola.es"
            val parse = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW,parse)
            startActivity(intent)
        }
        binding.btnWebview.setOnClickListener {
            binding.webviewBien.loadUrl("https://www.iberdrola.es")
            binding.webviewBien.visibility = View.VISIBLE
        }

    }
}