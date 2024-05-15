package com.example.doctorchatbotapp.Doctor.DoctorFragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.SharedPrefPkg.PrefManager;
import com.example.doctorchatbotapp.databinding.FragmentDoctorUpdateProfileBinding;
import com.example.doctorchatbotapp.databinding.FragmentUserPendingRequestBinding;
import com.example.doctorchatbotapp.registration.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DoctorUpdateProfileFragment extends Fragment {


    private FragmentDoctorUpdateProfileBinding binding;
    private String uid;
    private FirebaseAuth auth;
    private final int PICK_IMAGE_REQUEST = 26;
    private Uri imageUri;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String profileLink;
    public DoctorUpdateProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoctorUpdateProfileBinding.inflate(getLayoutInflater(),container,false);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();

        getUserData();
        binding.teacherProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE_REQUEST);
            }
        });
        binding.saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.teacherNameEd.getText().toString().trim();
//                String email = binding.teacherEmailEd.getText().toString().trim();
//                String password = binding.teacherPassED.getText().toString().trim();

                updateImg(name);

            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(requireActivity());
                prefManager.setCurrentstatus("");
                auth.signOut();
                startActivity(new Intent(requireActivity(), SignupActivity.class));
            }
        });
        return binding.getRoot();
    }

    private void getUserData() {
        if (uid != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("DoctorProfile").child("Details").child(uid);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        AddPatientDetailToRealtym userDetail = snapshot.getValue(AddPatientDetailToRealtym.class);

                        if (userDetail != null) {

                            Log.e("Chatbot","User : "+userDetail.getUsername());

                            String names = userDetail.getUsername();

                            binding.teacherNameEd.setText(names);


                            Glide.with(requireActivity()).load(userDetail.getImageurl()).placeholder(R.drawable.user_pro).into(binding.teacherProfileImage);

                            //                          Toast.makeText(getContext(), "User Found", Toast.LENGTH_SHORT).show();

                        } else {
                            // Handle the case where userDetail is null
                            //                          Toast.makeText(getContext(), "User details not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //                 Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateProfile(String name, String imageUri) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("DoctorProfile").child("Details").child(uid);

        Map<String, Object> updates = new HashMap<>();
        updates.put("username", name);
        updates.put("imageurl", imageUri);

        usersRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    binding.progressUpdate.setVisibility(View.GONE);
                    //                   Toast.makeText(getContext(), "Updated Profile", Toast.LENGTH_SHORT).show();
                    getUserData();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progressUpdate.setVisibility(View.GONE);
                //               Toast.makeText(getContext(), "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,
                resultCode,
                data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),
                        imageUri);
                binding.teacherProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateImg(String name) {
        if (imageUri != null) {
            binding.progressUpdate.setVisibility(View.VISIBLE);
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference = firebaseStorage.getReference();
            StorageReference imageRef = storageReference.child("Profile Images").child(uid);

            imageRef.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Image upload successful, now retrieve download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        profileLink = uri.toString();

                        updateProfile(name, profileLink);

                    }).addOnFailureListener(e -> {
                        binding.progressUpdate.setVisibility(View.GONE);
                        //                       Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    binding.progressUpdate.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}