package com.example.doctorchatbotapp.Doctor.DoctorFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentAllUserToDoctorBinding;
import com.example.doctorchatbotapp.databinding.FragmentHomeDoctorBinding;
import com.example.doctorchatbotapp.databinding.FragmentUserHomeBinding;


public class HomeDoctorFragment extends Fragment {


    private FragmentHomeDoctorBinding binding;
    public HomeDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeDoctorBinding.inflate(inflater,container,false);

        return binding.getRoot();

    }
}