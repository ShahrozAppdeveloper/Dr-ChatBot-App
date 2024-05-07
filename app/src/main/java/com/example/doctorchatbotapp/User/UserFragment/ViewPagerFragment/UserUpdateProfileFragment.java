package com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.SharedPrefPkg.PrefManager;
import com.example.doctorchatbotapp.databinding.FragmentUserPendingRequestBinding;
import com.example.doctorchatbotapp.databinding.FragmentUserUpdateProfileBinding;
import com.example.doctorchatbotapp.registration.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserUpdateProfileFragment extends Fragment {


   private FragmentUserUpdateProfileBinding binding;

    public UserUpdateProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserUpdateProfileBinding.inflate(getLayoutInflater(),container,false);
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                ProgressDialog progressDialog=new ProgressDialog(requireActivity());
                progressDialog.setMessage("Signout");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PrefManager prefManager=new PrefManager(requireActivity());
                        prefManager.setCurrentstatus("");
                        prefManager.setUserID("");
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Signout", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(requireActivity(), SignupActivity.class));
                    }
                },3000);
            }
        });
        return binding.getRoot();
    }
}