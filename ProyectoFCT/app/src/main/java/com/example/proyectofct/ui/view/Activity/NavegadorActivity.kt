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
            val url = "https://www.iberdrola.com"
            val parse = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, parse)
            startActivity(intent)
        }
        binding.btnWebview.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE


            binding.webviewBien.webViewClient = object : WebViewClient() {
                override fun onPageStarted(
                    view: WebView?,
                    url: String?,
                    favicon: android.graphics.Bitmap?
                ) {
                    super.onPageStarted(view, url, favicon)
                    binding.progressBar.visibility = View.GONE

                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }


            }
            binding.webviewBien.loadUrl("https://www.iberdrola.com")
            binding.webviewBien.visibility = View.VISIBLE
        }
        binding.btnBack.setOnClickListener {
            finish()
        }


    }
}