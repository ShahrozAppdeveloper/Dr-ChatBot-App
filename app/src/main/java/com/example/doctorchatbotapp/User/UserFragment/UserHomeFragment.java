package com.example.doctorchatbotapp.User.UserFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorchatbotapp.Admin.Adapter.UserToAdminAdapter;
import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.AllUserMedicaneActivity;
import com.example.doctorchatbotapp.User.UserAdapter.UserToMediAdapter;
import com.example.doctorchatbotapp.databinding.FragmentUnDoctorToUserBinding;
import com.example.doctorchatbotapp.databinding.FragmentUserHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserHomeFragment extends Fragment {

   private FragmentUserHomeBinding binding;
   DatabaseReference reference;
   FirebaseDatabase database;
   ArrayList<AddProdcutDetails> datalist;
    private UserToMediAdapter adapter;

    public UserHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserHomeBinding.inflate(getLayoutInflater(),container,false);
        GetMediDetail();
        binding.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), AllUserMedicaneActivity.class));
            }
        });
        return  binding.getRoot();
    }
    private void GetMediDetail(){
        datalist = new ArrayList<>();
        adapter = new UserToMediAdapter(datalist, requireActivity());

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Admin Product").child("Product Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        AddProdcutDetails model = snapshot1.getValue(AddProdcutDetails.class);
                        datalist.add(model);

                    }
                    binding.progressB.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }else{
                    binding.progressB.setVisibility(View.GONE);
                    Toast.makeText(requireActivity(), "no Medicane found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.rvmedicanID.setAdapter(adapter);
        binding.rvmedicanID.setLayoutManager(new GridLayoutManager(requireActivity(),2));

    }
}