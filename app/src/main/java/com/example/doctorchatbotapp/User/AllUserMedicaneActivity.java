package com.example.doctorchatbotapp.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserAdapter.UserToMediAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUserMedicaneActivity extends AppCompatActivity {
    private com.example.doctorchatbotapp.databinding.ActivityAllUserMedicaneBinding binding;
    DatabaseReference reference;
    FirebaseDatabase database;
    ArrayList<AddProdcutDetails> datalist;
    private UserToMediAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.doctorchatbotapp.databinding.ActivityAllUserMedicaneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         GetMediDetail();
    }

    private void GetMediDetail() {
        datalist = new ArrayList<>();
        adapter = new UserToMediAdapter(datalist, AllUserMedicaneActivity.this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Admin Product").child("Product Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        AddProdcutDetails model = snapshot1.getValue(AddProdcutDetails.class);
                        datalist.add(model);

                    }
                    binding.progressB.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {
                    binding.progressB.setVisibility(View.GONE);
                    Toast.makeText(AllUserMedicaneActivity.this, "no Medicane found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.rvmedicanID.setAdapter(adapter);
        binding.rvmedicanID.setLayoutManager(new GridLayoutManager(AllUserMedicaneActivity.this, 2));

    }
}