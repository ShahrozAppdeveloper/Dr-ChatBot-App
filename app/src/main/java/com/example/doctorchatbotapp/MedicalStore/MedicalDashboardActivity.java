package com.example.doctorchatbotapp.MedicalStore;

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

import com.example.doctorchatbotapp.ChatBotModule.ChatBotActivity;
import com.example.doctorchatbotapp.MedicalStore.Fragment.AddMediProductFragment;
import com.example.doctorchatbotapp.MedicalStore.Fragment.BookedProductFragment;
import com.example.doctorchatbotapp.MedicalStore.Fragment.MedicalHomeFragment;
import com.example.doctorchatbotapp.MedicalStore.Fragment.ViewAllProductFragment;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserDashboardActivity;
import com.example.doctorchatbotapp.User.UserFragment.AvaibleDoctorFragment;
import com.example.doctorchatbotapp.User.UserFragment.UserHomeFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserBookDoctorFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserUpdateProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MedicalDashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_dashboard);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.holdButtonGreen));

        bottomNavigationView = findViewById(R.id.medicalbottomNavigationView);
        ChangeFragment(new AddMediProductFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuId = item.getItemId();
                if (menuId == R.id.home) {
                    ChangeFragment(new AddMediProductFragment());
                } else if (menuId == R.id.viewAllProduct) {
                    ChangeFragment(new ViewAllProductFragment());
                } else if (menuId == R.id.bookedproduct) {
                    ChangeFragment(new BookedProductFragment());
                }
                else if (menuId == R.id.profile) {
                    ChangeFragment(new UserUpdateProfileFragment());
                }
                else {


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
        AlertDialog.Builder builder = new AlertDialog.Builder(MedicalDashboardActivity.this);
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