package com.example.doctorchatbotapp.Admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.doctorchatbotapp.Admin.DashboardFragment.ListofDoctorToAdminFragment;
import com.example.doctorchatbotapp.Admin.DashboardFragment.ListofuserToAdminFragment;
import com.example.doctorchatbotapp.ChatBotModule.ChatBotActivity;
import com.example.doctorchatbotapp.Doctor.DoctorDashboardActivity;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.AcceptAppointmentDoctorFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.DoctorUpdateProfileFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.HomeDoctorFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.PendingRequestDoctorTOUserFragment;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserFragment.UserHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminDashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.holdButtonGreen));

        bottomNavigationView = findViewById(R.id.userbottomNavigationView);
        ChangeFragment(new UserHomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuId = item.getItemId();
                if (menuId == R.id.adminhome) {
                    ChangeFragment(new HomeDoctorFragment());
                } else if (menuId == R.id.admindoctorlist) {
                    ChangeFragment(new ListofDoctorToAdminFragment());
                } else if (menuId == R.id.adminuserlist) {
                    ChangeFragment(new ListofuserToAdminFragment());
                } else if (menuId == R.id.adminprofile) {
                    ChangeFragment(new DoctorUpdateProfileFragment());
                }  else {


                }
                return true;
            }
        });
    }
    public void ChangeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrames,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboardActivity.this);
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            finishAffinity();
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}