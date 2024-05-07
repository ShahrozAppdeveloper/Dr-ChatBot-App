package com.example.doctorchatbotapp.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.doctorchatbotapp.ChatBotModule.ChatBotActivity;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.AcceptAppointmentDoctorFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.AllUserToDoctorFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.DoctorUpdateProfileFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.HomeDoctorFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.PendingRequestDoctorTOUserFragment;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.SharedPrefPkg.PrefManager;
import com.example.doctorchatbotapp.User.UserDashboardActivity;
import com.example.doctorchatbotapp.User.UserFragment.AvaibleDoctorFragment;
import com.example.doctorchatbotapp.User.UserFragment.UserHomeFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserBookDoctorFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserUpdateProfileFragment;
import com.example.doctorchatbotapp.registration.RegistrationOptionsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorDashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.holdButtonGreen));

        bottomNavigationView = findViewById(R.id.userbottomNavigationView);
        ChangeFragment(new HomeDoctorFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuId = item.getItemId();
                if (menuId == R.id.dochome) {
                    ChangeFragment(new HomeDoctorFragment());
                } else if (menuId == R.id.reqdoctor) {
                    ChangeFragment(new AllUserToDoctorFragment());
                } else if (menuId == R.id.chatwithai) {
                    Intent obj = new Intent(DoctorDashboardActivity.this, ChatBotActivity.class);
                    startActivity(obj);
                } else if (menuId == R.id.doctorprofile) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(DoctorDashboardActivity.this);
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