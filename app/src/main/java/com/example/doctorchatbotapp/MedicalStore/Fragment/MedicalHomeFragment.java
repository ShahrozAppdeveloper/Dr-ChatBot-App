package com.example.doctorchatbotapp.MedicalStore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentMedicalHomeBinding;
import com.example.doctorchatbotapp.databinding.FragmentUserHomeBinding;


public class MedicalHomeFragment extends Fragment {


    private FragmentMedicalHomeBinding binding;
    public MedicalHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMedicalHomeBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
}