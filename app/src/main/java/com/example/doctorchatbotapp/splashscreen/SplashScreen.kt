package com.example.doctorchatbotapp.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.doctorchatbotapp.R
import com.example.doctorchatbotapp.databinding.ActivitySignupBinding
import com.example.doctorchatbotapp.databinding.ActivitySplashScreenBinding
import com.example.doctorchatbotapp.onBoardingScreen.OnBoardingActivity
import com.example.doctorchatbotapp.registration.RegistrationOptionsActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.animationid.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, RegistrationOptionsActivity::class.java))
            },
            3000
        )
    }
}