package com.example.doctorchatbotapp.User;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.ActivityAppointDoctorBinding;

import java.util.Calendar;

public class AppointDoctorActivity extends AppCompatActivity {
    private ActivityAppointDoctorBinding binding;
    private String time, date;
    private String docName, docImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAppointDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        docName = intent.getStringExtra("docName");
        docImage = intent.getStringExtra("docImage");

        binding.docNameText.setText(docName);
        Glide.with(AppointDoctorActivity.this).load(docImage).placeholder(R.drawable.homeimage).into(binding.docImageView);
        binding.selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        binding.selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                AppointDoctorActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPm;
                        if (hourOfDay < 12) {
                            amPm = "AM";
                        } else {
                            amPm = "PM";
                            hourOfDay -= 12;
                        }

                        // Adjust hour for 12-hour format
                        if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }

                        // Format the time and update the EditText
                        time = String.format("%02d:%02d %s", hourOfDay, minute, amPm);
                        binding.timeSetText.setText(time);
                    }
                },
                hour, minute, false); // false for 12-hour format, true for 24-hour format

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AppointDoctorActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date
                        date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        binding.dateSetText.setText(date);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }
}