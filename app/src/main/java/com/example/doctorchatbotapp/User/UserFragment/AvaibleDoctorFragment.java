package com.example.doctorchatbotapp.User.UserFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserViewPagerAdapter.DoctorTouserViewPagerAdapter;
import com.example.doctorchatbotapp.databinding.FragmentAvaibleDoctorBinding;


public class AvaibleDoctorFragment extends Fragment {

    private FragmentAvaibleDoctorBinding binding;
    private DoctorTouserViewPagerAdapter adapter;

    public AvaibleDoctorFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAvaibleDoctorBinding.inflate(inflater,container,false);

        adapter = new DoctorTouserViewPagerAdapter(
                getChildFragmentManager());
        binding. viewPager.setAdapter(adapter);

        // It is used to join TabLayout with ViewPager.
        binding. tabLayout.setupWithViewPager( binding. viewPager);
        return binding.getRoot();
    }
}