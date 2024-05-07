package com.example.doctorchatbotapp.Admin.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.doctorchatbotapp.Admin.Fragment.ProvedDoctorToAdminFragment;
import com.example.doctorchatbotapp.Admin.Fragment.UnProvedDoctorToAdminFragment;



public class DoctorToAdminViewPagerAdapter
 extends FragmentPagerAdapter {

    public DoctorToAdminViewPagerAdapter(
@NonNull FragmentManager fm)
    { 
        super(fm); 
    } 
  
    @NonNull
    @Override
    public Fragment getItem(int position)
    { 
        Fragment fragment = null; 
        if (position == 0)
            fragment = new UnProvedDoctorToAdminFragment();
        else if (position == 1) 
            fragment = new ProvedDoctorToAdminFragment();


  
        return fragment; 
    } 
  
    @Override
    public int getCount() 
    { 
        return 2;
    } 
  
    @Override
    public CharSequence getPageTitle(int position) 
    { 
        String title = null; 
        if (position == 0) 
            title = "not Approved";
        else if (position == 1) 
            title = "Approved";
        return title; 
    } 
} 