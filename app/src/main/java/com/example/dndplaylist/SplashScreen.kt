package com.example.dndplaylist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import start.StartView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, StartView::class.java)
        startActivity(intent)
        finish()
    }
}