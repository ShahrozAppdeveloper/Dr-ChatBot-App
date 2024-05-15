package com.example.doctorchatbotapp.MedicalStore.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserAdapter.UserToMediAdapter;
import com.example.doctorchatbotapp.databinding.FragmentAddMediProductBinding;
import com.example.doctorchatbotapp.databinding.FragmentBookedProductBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BookedProductFragment extends Fragment {

    private FragmentBookedProductBinding binding;
    DatabaseReference reference;
    FirebaseDatabase database;
    ArrayList<AddProdcutDetails> datalist;
    private UserToMediAdapter adapter;
    public BookedProductFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookedProductBinding.inflate(getLayoutInflater(),container,false);
         GetMediDetail();
        return binding.getRoot();
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
                    Toast.makeText(requireActivity(), "no Medicine found", Toast.LENGTH_SHORT).show();
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