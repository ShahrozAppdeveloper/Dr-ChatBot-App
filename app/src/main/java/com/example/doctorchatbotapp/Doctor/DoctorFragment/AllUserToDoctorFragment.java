package com.example.doctorchatbotapp.Doctor.DoctorFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorchatbotapp.Doctor.DoctorViewPagerAdpater.UserToDoctorViewPagerAdapter;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserViewPagerAdapter.DoctorTouserViewPagerAdapter;
import com.example.doctorchatbotapp.databinding.FragmentAllUserToDoctorBinding;
import com.example.doctorchatbotapp.databinding.FragmentAvaibleDoctorBinding;


public class AllUserToDoctorFragment extends Fragment {


   private FragmentAllUserToDoctorBinding binding;
   private UserToDoctorViewPagerAdapter adapter;
    public AllUserToDoctorFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllUserToDoctorBinding.inflate(inflater,container,false);
        adapter = new UserToDoctorViewPagerAdapter(
                getChildFragmentManager());
        binding. viewPager.setAdapter(adapter);

        // It is used to join TabLayout with ViewPager.
        binding. tabLayout.setupWithViewPager( binding. viewPager);
        return binding.getRoot();
    }
}