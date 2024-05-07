package com.example.doctorchatbotapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.ActivityUserViewMedicalInfoBinding;

public class UserViewMedicalInfoActivity extends AppCompatActivity {
    private ActivityUserViewMedicalInfoBinding binding;
    String productID, productName, productImage, productPrice, ownerID;
    private int countNumber = 1;
    private int savedCount;
    private int originalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewMedicalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        productID = intent.getStringExtra("productID");
        productName = intent.getStringExtra("productName");
        productImage = intent.getStringExtra("productImage");
        productPrice = intent.getStringExtra("productPrice");
        ownerID = intent.getStringExtra("ownerID");

        savedCount = Integer.parseInt(productPrice);
        originalPrice = savedCount;

        binding.additembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

        binding.removeitembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
        binding.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserViewMedicalInfoActivity.this, MedicaneDetailsScreenActivity.class)
                        .putExtra("itemName", productName)
                        .putExtra("totalPrice", String.valueOf(savedCount))
                        .putExtra("itemImage", productImage)
                        .putExtra("productID", productID)
                        .putExtra("ownerID", ownerID)
                        .putExtra("quantity", String.valueOf(countNumber)));

            }
        });

        SetDetails();

    }
    private void SetDetails() {
        Glide.with(getApplicationContext()).load(productImage).into(binding.foodimagemain);
        binding.foodnamemain.setText(productName);
        binding.priceXppTextmain.setText(productPrice);
    }

    private void increment() {
        countNumber++;
        savedCount += originalPrice;
        updateTotalPrice();
    }

    private void decrement() {
        if (countNumber > 1) {
            countNumber--;
            savedCount -= originalPrice;
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        binding.itemnumbers.setText(String.valueOf(countNumber));
        binding.totalPriceTextView.setText("PKR " + String.valueOf(savedCount));
    }
}