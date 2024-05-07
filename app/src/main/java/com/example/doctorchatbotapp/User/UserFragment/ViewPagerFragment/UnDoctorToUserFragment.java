package com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserAdapter.DoctorToUserAdapter;
import com.example.doctorchatbotapp.databinding.FragmentUnDoctorToUserBinding;
import com.example.doctorchatbotapp.databinding.FragmentUserPendingRequestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UnDoctorToUserFragment extends Fragment {


   private FragmentUnDoctorToUserBinding binding;
    private DoctorToUserAdapter adapter;
    private ArrayList<AddPatientDetailToRealtym> list;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public UnDoctorToUserFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUnDoctorToUserBinding.inflate(getLayoutInflater(),container,false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            String currentUserId = user.getUid();
            list = new ArrayList<>();
            adapter = new DoctorToUserAdapter(list, requireActivity(), currentUserId);
            binding.progressB.setVisibility(View.VISIBLE);

            reference = FirebaseDatabase.getInstance().getReference().child("DoctorProfile").child("Details");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        AddPatientDetailToRealtym model = snapshot1.getValue(AddPatientDetailToRealtym.class);
                        if (model != null && "notbook".equals(model.getBookingstatus()) && "proved".equals(model.getProvedstatus())) {
                            list.add(model);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    binding.progressB.setVisibility(View.GONE);

                    if (list.isEmpty()) {
                        Toast.makeText(requireActivity(), "No Doctor found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    binding.progressB.setVisibility(View.GONE);
                    Toast.makeText(requireActivity(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            binding.dcotoruserRecyclerViewID.setAdapter(adapter);
            binding.dcotoruserRecyclerViewID.setLayoutManager(new LinearLayoutManager(requireActivity()));
        } else {
            // Handle the case where the user is null
        }


        return binding.getRoot();
    }
}