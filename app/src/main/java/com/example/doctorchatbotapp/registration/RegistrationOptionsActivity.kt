package com.example.doctorchatbotapp.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.doctorchatbotapp.Admin.AdminDashboardActivity
import com.example.doctorchatbotapp.Admin.AdminProfileActivity
import com.example.doctorchatbotapp.Doctor.DoctorProfileActivity
import com.example.doctorchatbotapp.MedicalStore.MedicalDashboardActivity
import com.example.doctorchatbotapp.SharedPrefPkg.PrefManager
import com.example.doctorchatbotapp.User.UserProfileActivity
import com.example.doctorchatbotapp.databinding.ActivityRegisterationOptionsBinding


class RegistrationOptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterationOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterationOptionsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.appCompatButton2.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }
        binding.tvsignupid.setOnClickListener {
            startActivity(Intent(applicationContext,SignupActivity::class.java))
        }

    }
    override fun onStart() {
        super.onStart()
        val prefManager1 = PrefManager(applicationContext)
        if (prefManager1.getCurrentstatus().equals("Admin")) {
            //step 1.
            val intent = Intent(applicationContext, AdminDashboardActivity::class.java)
            startActivity(intent)
        } else if (prefManager1.getCurrentstatus().equals("Doctor")) {
            val intent = Intent(applicationContext, DoctorProfileActivity::class.java)
            startActivity(intent)
        }
        else if (prefManager1.getCurrentstatus().equals("Patient")) {
            val intent = Intent(applicationContext, UserProfileActivity::class.java)
            startActivity(intent)
        }
        else if (prefManager1.getCurrentstatus().equals("Medical")) {
            val intent = Intent(applicationContext, MedicalDashboardActivity::class.java)
            startActivity(intent)
        }
        else {
        }
    }
}