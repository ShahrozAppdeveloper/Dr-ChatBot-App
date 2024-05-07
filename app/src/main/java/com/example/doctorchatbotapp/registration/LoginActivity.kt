package com.example.doctorchatbotapp.registration

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doctorchatbotapp.Admin.AdminDashboardActivity
import com.example.doctorchatbotapp.Admin.AdminProfileActivity
import com.example.doctorchatbotapp.Doctor.DoctorProfileActivity
import com.example.doctorchatbotapp.ModelClass.CurrentStatusDetails
import com.example.doctorchatbotapp.SharedPrefPkg.PrefManager
import com.example.doctorchatbotapp.User.UserProfileActivity
import com.example.doctorchatbotapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    var currentstatus: String? = null
    var dialog: ProgressDialog? = null
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database:FirebaseDatabase
    private lateinit var reference: DatabaseReference
    var prefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding.root
        prefManager = PrefManager(this)
        reference =
            FirebaseDatabase.getInstance().getReference("Dr Chatbot App").child("Account Details")

        clicklistener()
        setContentView(view)

    }
    private fun clicklistener(){
        binding.createAccountTV.setOnClickListener {
            startActivity(Intent(applicationContext,SignupActivity::class.java))
        }
        binding.imageView2.setOnClickListener {
            startActivity(Intent(applicationContext,SignupActivity::class.java))
        }
        binding.forgetPassword.setOnClickListener {
            startActivity(Intent(applicationContext,ForgotPasswordActitvity::class.java))
        }
        binding.loginButton.setOnClickListener {
            if (binding.edmailid.getText().toString().isEmpty() ||
               binding.edpassid.getText().toString().isEmpty()
            ) {
                Toast.makeText(this@LoginActivity, "Enter Email and password", Toast.LENGTH_SHORT)
                    .show()
            } else if (!binding.edmailid.getText().toString().contains("@gmail.com")) {
                Toast.makeText(this@LoginActivity, "Please Enter valid Email", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.edpassid.getText().toString().length < 6) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please Enter valid Password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                dialog = ProgressDialog(this@LoginActivity)
                dialog!!.setMessage("please wait...")
                dialog!!.show()
                signIn(binding.edmailid.getText().toString(), binding.edpassid.getText().toString())
            }
        }
    }
    private fun signIn(email: String, pass: String) {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let {
                    val userId = it.uid
                    reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val currentStatusDetails: CurrentStatusDetails? = snapshot.getValue(CurrentStatusDetails::class.java)
                            currentStatusDetails?.let { details ->
                                when (details.currentstatus) {
                                    "Admin" -> {
                                        startActivity(Intent(applicationContext, AdminDashboardActivity::class.java))
                                    }
                                    "Patient" -> {
                                        startActivity(Intent(applicationContext, UserProfileActivity::class.java))
                                    }
                                    "Doctor" -> {
                                        startActivity(Intent(applicationContext, DoctorProfileActivity::class.java))
                                    }
                                    else -> {
                                        Toast.makeText(this@LoginActivity, "Unknown User Type", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            dialog?.dismiss()
                            Toast.makeText(this@LoginActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            } else {
                dialog?.dismiss()
                Toast.makeText(this@LoginActivity, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

