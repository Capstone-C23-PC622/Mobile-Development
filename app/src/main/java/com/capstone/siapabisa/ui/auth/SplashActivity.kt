package com.capstone.siapabisa.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.ui.user.MainActivity
import com.capstone.siapabisa.databinding.ActivitySplashBinding
import com.capstone.siapabisa.ui.usaha.UsahaMainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId()
        val role = preferences.getRole()

        val token = ""

        Handler(Looper.getMainLooper()).postDelayed({
            if(userId != null && role == 1){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            if(userId != null && role == 2){
                val intent = Intent(this, UsahaMainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this, LoginRegister::class.java)
                startActivity(intent)
                finish()
            }
            finish()
        }, 1000L)


    }
}