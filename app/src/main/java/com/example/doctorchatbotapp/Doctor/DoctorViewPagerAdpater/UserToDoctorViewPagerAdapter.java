package com.example.doctorchatbotapp.Doctor.DoctorViewPagerAdpater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.doctorchatbotapp.Doctor.DoctorFragment.AcceptAppointmentDoctorFragment;
import com.example.doctorchatbotapp.Doctor.DoctorFragment.PendingRequestDoctorTOUserFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UnDoctorToUserFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserBookDoctorFragment;
import com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment.UserPendingRequestFragment;


public class UserToDoctorViewPagerAdapter
 extends FragmentPagerAdapter {

    public UserToDoctorViewPagerAdapter(
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
            fragment = new PendingRequestDoctorTOUserFragment();
        else if (position == 1) 
            fragment = new AcceptAppointmentDoctorFragment();



  
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
            title = "Pending";
        else if (position == 1) 
            title = "Book Appointment";

        return title; 
    } 
} 