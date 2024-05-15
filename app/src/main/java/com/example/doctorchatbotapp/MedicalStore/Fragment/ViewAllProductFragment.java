package com.example.doctorchatbotapp.MedicalStore.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.doctorchatbotapp.MedicalStore.Adapter.MediViewProductAdapter;
import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.ModelClass.ConfrimCartDetails;
import com.example.doctorchatbotapp.databinding.FragmentAddMediProductBinding;
import com.example.doctorchatbotapp.databinding.FragmentViewAllProductBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewAllProductFragment extends Fragment {

    private FragmentViewAllProductBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    ArrayList<ConfrimCartDetails> list;
    MediViewProductAdapter adapter;
    private String userID;
    public ViewAllProductFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewAllProductBinding.inflate(getLayoutInflater(),container,false);
        list = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userID = currentUser.getUid();
            Toast.makeText(requireActivity(), ""+userID, Toast.LENGTH_SHORT).show();
            getData(userID);
            adapter = new MediViewProductAdapter(list,requireActivity());

            binding.viewAllProductsRecyclerView.setAdapter(adapter);
            binding.viewAllProductsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        }


        return binding.getRoot();
    }



    // ViewAllProductFragment.java
    private void getData(String userID){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("OwnerMedicine").child("Details");

        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        ConfrimCartDetails details = snapshot1.getValue(ConfrimCartDetails.class);
                        if (details != null){
                            list.add(details);
                        } else {
                            Toast.makeText(requireActivity(), "", Toast.LENGTH_SHORT).show();
                            // Handle the case when data is null
                        }
                    }
                    adapter.notifyDataSetChanged(); // Move this line outside the loop to avoid redundant calls
                } else {
                    // Handle the case when snapshot does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}