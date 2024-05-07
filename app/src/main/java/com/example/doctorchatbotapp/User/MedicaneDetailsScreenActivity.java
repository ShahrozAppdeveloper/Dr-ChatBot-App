package com.example.doctorchatbotapp.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.ModelClass.AddItemToCartDetails;
import com.example.doctorchatbotapp.databinding.ActivityMedicaneDetailsScreenBinding;

import java.util.ArrayList;

public class MedicaneDetailsScreenActivity extends AppCompatActivity {
    private ActivityMedicaneDetailsScreenBinding binding;
    ArrayList<AddItemToCartDetails> datalist;
    String productID, productName, productImage, productPrice, ownerID,quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicaneDetailsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        datalist = new ArrayList<>();
        Intent intent = getIntent();
        productID = intent.getStringExtra("productID");
        productName = intent.getStringExtra("itemName");
        productImage = intent.getStringExtra("itemImage");
        productPrice = intent.getStringExtra("totalPrice");
        quantity = intent.getStringExtra("quantity");
        ownerID = intent.getStringExtra("ownerID");


    }
}