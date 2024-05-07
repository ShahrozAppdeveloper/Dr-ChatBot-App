package com.example.doctorchatbotapp.Admin.DashboardFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorchatbotapp.Admin.ViewPagerAdapter.DoctorToAdminViewPagerAdapter;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentListofDoctorToAdminBinding;


public class ListofDoctorToAdminFragment extends Fragment {

  private FragmentListofDoctorToAdminBinding binding;
   private DoctorToAdminViewPagerAdapter adapter;
    public ListofDoctorToAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListofDoctorToAdminBinding.inflate(getLayoutInflater(),container,false);
        adapter = new DoctorToAdminViewPagerAdapter(
                getChildFragmentManager());
        binding. viewPager.setAdapter(adapter);

        // It is used to join TabLayout with ViewPager.
        binding. tabLayout.setupWithViewPager( binding. viewPager);
        return  binding.getRoot();
    }
}