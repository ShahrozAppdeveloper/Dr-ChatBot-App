package com.example.doctorchatbotapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.ModelClass.AddItemToCartDetails;
import com.example.doctorchatbotapp.User.ModelClass.UserCartDetailsModelClass;
import com.example.doctorchatbotapp.User.UserAdapter.CartToUserAdapter;
import com.example.doctorchatbotapp.databinding.ActivityMedicaneDetailsScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedicaneDetailsScreenActivity extends AppCompatActivity {
    private ActivityMedicaneDetailsScreenBinding binding;
    ArrayList<AddItemToCartDetails> datalist;
    String productID, productName, productImage, productPrice, ownerID,quantity;
    private CartToUserAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference reference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicaneDetailsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        datalist = new ArrayList<>();
        Intent intent = getIntent();
        productID = intent.getStringExtra("productID");
        productName = intent.getStringExtra("itemName");
        productImage = intent.getStringExtra("itemImage");
        productPrice = intent.getStringExtra("totalPrice");
        quantity = intent.getStringExtra("quantity");
        ownerID = intent.getStringExtra("ownerID");
        if (currentUser != null) {
            userID = currentUser.getUid();

            AddCartitems(userID, productID, ownerID, quantity, productImage,productPrice );
        } else {

        }
        binding.scannedItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartToUserAdapter(datalist,this);
        binding.scannedItemRecyclerView.setAdapter(adapter);
        ViewCartItems();

        // click listener
        binding.proceedToPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MedicaneDetailsScreenActivity.this,UserPaymentScreenActivity.class)
                        .putExtra("list",datalist));
            }
        });
    }
    private void AddCartitems(String userID,String productID,String ownerID,String quantity,String productImage,String productprice){
        database = FirebaseDatabase.getInstance();
        AddItemToCartDetails obj = new AddItemToCartDetails(userID,ownerID,productID,productImage,productName,productprice,quantity);
        reference = database.getReference("UserCart").child("Details");
        reference.child(userID).child(productID).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MedicaneDetailsScreenActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ViewCartItems() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("UserCart").child("Details").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear(); // Clear existing data before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddItemToCartDetails item = dataSnapshot.getValue(AddItemToCartDetails.class);
                    if (item != null) {
                        datalist.add(item);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event if needed
            }
        });
    }

}