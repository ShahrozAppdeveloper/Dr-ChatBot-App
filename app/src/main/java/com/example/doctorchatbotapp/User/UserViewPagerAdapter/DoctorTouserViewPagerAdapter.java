package com.example.doctorchatbotapp.User.UserViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UnDoctorToUserFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserBookDoctorFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserPendingRequestFragment;



public class DoctorTouserViewPagerAdapter
 extends FragmentPagerAdapter {

    public DoctorTouserViewPagerAdapter(
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
            fragment = new UnDoctorToUserFragment();
        else if (position == 1) 
            fragment = new UserPendingRequestFragment();



  
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
            title = "UnBook";
        else if (position == 1) 
            title = "Pending";
        return title; 
    } 
} 