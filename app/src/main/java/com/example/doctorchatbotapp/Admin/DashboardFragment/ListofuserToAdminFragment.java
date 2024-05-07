package com.example.doctorchatbotapp.Admin.DashboardFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorchatbotapp.Admin.Adapter.UserToAdminAdapter;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentListofuserToAdminBinding;
import com.example.doctorchatbotapp.databinding.FragmentViewMediBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListofuserToAdminFragment extends Fragment {


   private FragmentListofuserToAdminBinding binding;
    private UserToAdminAdapter adapter;
    ArrayList<AddPatientDetailToRealtym> list;
    DatabaseReference reference;
    public ListofuserToAdminFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListofuserToAdminBinding.inflate(getLayoutInflater(),container,false);
        list = new ArrayList<>();

        adapter = new UserToAdminAdapter(list, requireActivity());
        binding.progressB.setVisibility(View.VISIBLE);

        reference = FirebaseDatabase.getInstance().getReference("ProfileUser").child(
                "User");

        reference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if (snapshot.exists()){
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                AddPatientDetailToRealtym model = snapshot1.getValue(AddPatientDetailToRealtym.class);
                                list.add(model);

                            }
                            binding.progressB.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }else{
                            binding.progressB.setVisibility(View.GONE);
                            Toast.makeText(requireActivity(), "no user found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });

        binding.userAdminRecyclerViewID.setAdapter(adapter);
        binding.userAdminRecyclerViewID.setLayoutManager(new GridLayoutManager(requireActivity(),2));
        return binding.getRoot();
    }
}