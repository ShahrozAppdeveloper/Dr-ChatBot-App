package com.example.doctorchatbotapp.Admin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorchatbotapp.Admin.Adapter.ProveDoctorAdminAdapter;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentProvedDoctorToAdminBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProvedDoctorToAdminFragment extends Fragment {

  private FragmentProvedDoctorToAdminBinding binding;
    private ProveDoctorAdminAdapter adapter;
    ArrayList<AddPatientDetailToRealtym> list;
    DatabaseReference reference;

    public ProvedDoctorToAdminFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProvedDoctorToAdminBinding.inflate(getLayoutInflater(),container,false);
        list = new ArrayList<>();

        adapter = new ProveDoctorAdminAdapter(list, requireActivity());
        binding.progressB.setVisibility(View.VISIBLE);

        reference = FirebaseDatabase.getInstance().getReference("DoctorProfile").child("Details");

        reference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if (snapshot.exists()){
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                AddPatientDetailToRealtym model = snapshot1.getValue(AddPatientDetailToRealtym.class);
                                String status = model.getProvedstatus();
                                if (status.equals("proved")) {
                                    list.add(model);
                                }
                            }
                            binding.progressB.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }else{
                            binding.progressB.setVisibility(View.GONE);
                            Toast.makeText(requireActivity(), "no Doctor found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });

        binding.doctorAdminRecyclerViewID.setAdapter(adapter);
        binding.doctorAdminRecyclerViewID.setLayoutManager(new LinearLayoutManager(requireActivity()));
        return binding.getRoot();
    }
}