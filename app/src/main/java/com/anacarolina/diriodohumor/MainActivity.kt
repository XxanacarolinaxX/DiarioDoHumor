package com.anacarolina.diriodohumor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val filter = listOf("Feliz", "Triste", "Raiva", "Medo", "Inveja", "Ansiedade", "Tedio")
}