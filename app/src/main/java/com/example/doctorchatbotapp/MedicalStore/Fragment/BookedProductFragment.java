package com.example.doctorchatbotapp.MedicalStore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentAddMediProductBinding;
import com.example.doctorchatbotapp.databinding.FragmentBookedProductBinding;


public class BookedProductFragment extends Fragment {

    private FragmentBookedProductBinding binding;
    public BookedProductFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookedProductBinding.inflate(getLayoutInflater(),container,false);

        return binding.getRoot();
    }
}