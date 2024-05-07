package com.example.doctorchatbotapp.registration;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorchatbotapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActitvity extends AppCompatActivity {
    Button btnsnnemail;
    TextView tvlogin;
    EditText edemail;
    private FirebaseAuth mAuth;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_actitvity);
        IDs();
        Clicklistener();
        changeColorStatusBar(R.color.goButtonGreen);
    }
    private void IDs(){
        btnsnnemail=findViewById(R.id.loginButton);
        edemail=findViewById(R.id.edmailid);
        tvlogin=findViewById(R.id.createAccountTV);
        imageView=findViewById(R.id.imageView2);
    }
    private void Clicklistener(){
        btnsnnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edemail.getText().toString().isEmpty()
                ) {
                    Toast.makeText(ForgotPasswordActitvity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                } else if (!edemail.getText().toString().contains("@gmail.com")) {
                    Toast.makeText(ForgotPasswordActitvity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    ValidData(edemail.getText().toString());
                }
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActitvity.this,LoginActivity.class));

            }
        });
    }
    private void ValidData(String email){
        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActitvity.this, "PLz Check your email", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPasswordActitvity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void changeColorStatusBar(@ColorRes int color) {
        Window window = getWindow();
        View decorView = window.getDecorView();
        WindowInsetsControllerCompat wic = new WindowInsetsControllerCompat(window, decorView);
        wic.setAppearanceLightStatusBars(true);
        // You can set any background color to the status bar.
        window.setStatusBarColor(ContextCompat.getColor(this, color));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgotPasswordActitvity.this,LoginActivity.class));
    }
}