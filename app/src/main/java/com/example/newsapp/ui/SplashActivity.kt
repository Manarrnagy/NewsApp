package com.example.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.newsapp.R
import com.example.newsapp.ui.home.fragments.NewsFragment

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(
            object:Runnable{
                override fun run() {
                    val intent = Intent(this@SplashActivity, NewsFragment::class.java)
                    startActivity(intent)
                }
            },
        2000)
    }

}