package com.example.doctorchatbotapp.registration

import android.R
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.doctorchatbotapp.Admin.AdminDashboardActivity
import com.example.doctorchatbotapp.Doctor.DoctorProfileActivity
import com.example.doctorchatbotapp.MedicalStore.MedicalDashboardActivity
import com.example.doctorchatbotapp.ModelClass.CurrentStatusDetails
import com.example.doctorchatbotapp.SharedPrefPkg.PrefManager
import com.example.doctorchatbotapp.User.UserProfileActivity
import com.example.doctorchatbotapp.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var tvlogin: TextView
    private lateinit var currentstatus: String

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dialog: ProgressDialog
    private lateinit var currentUser: String
    private var uid: String? = null
    private lateinit var alertDialog: AlertDialog
    private lateinit var binding: ActivitySignupBinding
    var prefManager: PrefManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view: View = binding.root

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Doctor Chatbot App")
        alertDialogBuilder.setMessage("Do you want to close the App?")
        alertDialogBuilder.setPositiveButton(
            "Yes"
        ) { dialog, which -> // Close the activity
            finishAffinity()
        }
        alertDialogBuilder.setNegativeButton(
            "No"
        ) { dialog, which -> // Hide the dialog (do nothing)
            dialog.dismiss()
        }

        alertDialog = alertDialogBuilder.create()
        // calling method
        // calling method

        clicklistener()
//        changeColorStatusBar(R.color.goButtonGreen)

        val arrayList = ArrayList<String>()
//        arrayList.add("Lawyer");
        //        arrayList.add("Lawyer");
        arrayList.add("Doctor")
        arrayList.add("Patient")
        arrayList.add("Admin")
        arrayList.add("Medical")
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerid.adapter = arrayAdapter
        binding.spinnerid.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                currentstatus = parent.getItemAtPosition(position).toString()
                Toast.makeText(parent.context, "Selected:$currentstatus", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        setContentView(view)

        binding.password.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd: Drawable? = binding.password.compoundDrawablesRelative[2]
                drawableEnd?.let {
                    if (event.rawX >= (binding.password.right - it.bounds.width())) {
                        togglePasswordVisibility()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    private fun clicklistener() {
        binding.loginTextView.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        binding.imageView2.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        binding.registerButton.setOnClickListener {

            if (
                binding.password.text.toString().isEmpty() ||
                binding.email.text.toString().isEmpty()
            ) {
                Toast.makeText(this@SignupActivity, "Enter Detail please", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.password.text.toString().length < 6) {
                Toast.makeText(this@SignupActivity, "Enter valid password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                dialog = ProgressDialog(this@SignupActivity)
                dialog!!.setMessage("please wait...")
                dialog!!.setTitle("DoctorChatApp")
                dialog!!.show()
                Signup(binding.email.text.toString(), binding.password.text.toString())
            }
        }
    }

    private fun Signup(email: String, pass: String) {
        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()
        mAuth = FirebaseAuth.getInstance()
        //        String user=FirebaseAuth.getInstance().getCurrentUser().getUid();
//        currentUser=user.toString();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = mAuth.getCurrentUser()
                val uid = user!!.uid
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("Status", currentstatus)
                Toast.makeText(
                    this@SignupActivity,
                    "welcome" + " " + currentstatus.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(intent)
                AddUSerDataToRealtime(
                    uid,
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
                dialog.dismiss()
            }
        }?.addOnFailureListener { e ->
            Toast.makeText(
                this@SignupActivity,
                "Error" + e.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun AddUSerDataToRealtime(userid: String, email: String, pass: String) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference =
            database.getReference("Dr Chatbot App").child("Account Details")
        val obj = CurrentStatusDetails(
            userid,
            pass,
            email,
            currentstatus
        )
        myRef.child(userid).setValue(obj).addOnCompleteListener(OnCompleteListener<Void?> { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@SignupActivity, "Sucesfully Updated", Toast.LENGTH_SHORT)
                    .show()
            }
        }).addOnFailureListener(OnFailureListener { e ->
            Toast.makeText(
                this@SignupActivity,
                "" + e.message,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onStart() {
        super.onStart()
        val prefManager1 = PrefManager(applicationContext)
        if (prefManager1.currentstatus.equals("Doctor")) {
            //step 1.
            val intent = Intent(applicationContext, DoctorProfileActivity ::class.java)
            startActivity(intent)
        } else if (prefManager1.currentstatus.equals("Patient")) {
            val intent = Intent(applicationContext, UserProfileActivity::class.java)
            startActivity(intent)
        } else if (prefManager1.currentstatus.equals("Medical")) {
            val intent = Intent(applicationContext, MedicalDashboardActivity::class.java)
            startActivity(intent)
        } else if (prefManager1.currentstatus.equals("Admin")) {
            val intent = Intent(applicationContext, AdminDashboardActivity::class.java)
            startActivity(intent)
        }
        // step 2
//        }else if (prefManager1.getCurrentstatus().equals("Admin")){
//            //step 2
//            Intent intent=new Intent(getApplicationContext(), AdminDashboard.class);
//            startActivity(intent);
//        }
    }
    override fun onBackPressed() {
        alertDialog.show()
    }

    fun changeColorStatusBar(@ColorRes color: Int) {
        val window = window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true
        window.statusBarColor = ContextCompat.getColor(this, color)
    }

    private fun togglePasswordVisibility() {
        val selection = binding.password.selectionEnd
        if (binding.password.transformationMethod == PasswordTransformationMethod.getInstance()) {
            binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                com.example.doctorchatbotapp.R.drawable.lock, 0, com.example.doctorchatbotapp.R.drawable.eye, 0
            )
        } else {
            binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                com.example.doctorchatbotapp.R.drawable.lock, 0, com.example.doctorchatbotapp.R.drawable.eye_hide, 0
            )
        }
        binding.password.setSelection(selection)
    }
}