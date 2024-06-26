package com.ourapps.mymovielist.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.ourapps.mymovielist.R
import com.ourapps.mymovielist.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate((layoutInflater))
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 1500)
    }
}