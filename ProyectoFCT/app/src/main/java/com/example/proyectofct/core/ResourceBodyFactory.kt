package com.example.proyectofct.core

import android.R
import android.content.Context
import co.infinum.retromock.BodyFactory
import java.io.FileInputStream
import java.io.InputStream

class ResourceBodyFactory(private val context: Context) : BodyFactory {
    override fun create(input: String): InputStream {
        return context.assets.open(input)
    }
}