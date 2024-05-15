package com.example.doctorchatbotapp.Doctor.DoctorFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorchatbotapp.Doctor.Adapter.ConFrimBookDoctorAdapter;
import com.example.doctorchatbotapp.ModelClass.AddRequserDetailsToDatabase;
import com.example.doctorchatbotapp.ModelClass.MemberBookingDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentAllUserToDoctorBinding;
import com.example.doctorchatbotapp.databinding.FragmentHomeDoctorBinding;
import com.example.doctorchatbotapp.databinding.FragmentUserHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeDoctorFragment extends Fragment {


    private FragmentHomeDoctorBinding binding;
    private ConFrimBookDoctorAdapter adapter;
    ArrayList<AddRequserDetailsToDatabase> list;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String reqID;
    public HomeDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeDoctorBinding.inflate(inflater,container,false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String currentUserId = user.getUid();

            Toast.makeText(requireActivity(), "" + currentUserId, Toast.LENGTH_SHORT).show();
            list = new ArrayList<>();
            adapter = new ConFrimBookDoctorAdapter(list, requireActivity(),currentUserId);
            binding.progressB.setVisibility(View.VISIBLE);
            reference = FirebaseDatabase.getInstance().getReference("DoctorBooking").child("Info");

            reference.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MemberBookingDetails model = dataSnapshot.getValue(MemberBookingDetails.class);
                            if (model != null && "Accept".equals(model.getReqstatus())) {
                                String userId = model.getMemberID();
                                reqID = model.getReqID();
                                DatabaseReference trainerRef = FirebaseDatabase.getInstance().getReference("ProfileUser").child(
                                        "User").child(userId);
                                trainerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            AddRequserDetailsToDatabase userDetail = snapshot.getValue(AddRequserDetailsToDatabase.class);
                                            if (userDetail != null) {
                                                String username = userDetail.getUsername();
                                                String userImage = userDetail.getImageurl();
                                                list.add(new AddRequserDetailsToDatabase(userId,username,userImage,reqID));

                                            }
                                            adapter.notifyDataSetChanged();

                                        }
                                        binding.progressB.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        binding.progressB.setVisibility(View.GONE);
                                        Toast.makeText(requireActivity(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    } else {
                        binding.progressB.setVisibility(View.GONE);
                        Toast.makeText(requireActivity(), "No Pending Booking found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    binding.progressB.setVisibility(View.GONE);
                    Toast.makeText(requireActivity(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            binding.rvbookID.setAdapter(adapter);
            binding.rvbookID.setLayoutManager(new LinearLayoutManager(requireActivity()));
        } else {
            // Handle the case where the user is null
        }
        return binding.getRoot();

    }
}