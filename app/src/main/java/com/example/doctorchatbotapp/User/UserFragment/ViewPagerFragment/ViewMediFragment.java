package com.example.doctorchatbotapp.User.UserFragment.ViewPagerFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.doctorchatbotapp.MedicalStore.Adapter.MediViewProductAdapter;
import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.ModelClass.ConfrimCartDetails;
import com.example.doctorchatbotapp.databinding.FragmentAddMediProductBinding;
import com.example.doctorchatbotapp.databinding.FragmentViewMediBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewMediFragment extends Fragment {

   private FragmentViewMediBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    ArrayList<ConfrimCartDetails> list;
    ArrayList<ConfrimCartDetails> filteredUserList;
    MediViewProductAdapter adapter;

    public ViewMediFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewMediBinding.inflate(getLayoutInflater(),container,false);
        database = FirebaseDatabase.getInstance();

        list = new ArrayList<>();
        filteredUserList = new ArrayList<>();
        adapter = new MediViewProductAdapter(filteredUserList,requireContext());

        binding.viewAllProductsRecyclerView.setAdapter(adapter);
        binding.viewAllProductsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        getData();
        filteredUserList.addAll(list);
        binding.searchViewUsersList.setQueryHint("Search Product");
        binding.searchViewUsersList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return true;
            }
        });

        return binding.getRoot();
    }

    private void filterUsers(String searchText) {
        filteredUserList.clear();

        if (TextUtils.isEmpty(searchText)) {
            // If search text is empty, show all items
            filteredUserList.addAll(list);
        } else {
            // Filter by user name or email
            for (ConfrimCartDetails user : list) {
                if (user.getName().toLowerCase().contains(searchText.toLowerCase())
                        || user.getPrice().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredUserList.add(user);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void getData(){
        reference = database.getReference(
                "UserCartDetails");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        ConfrimCartDetails details = snapshot1.getValue(ConfrimCartDetails.class);
                        if (details != null){
                            list.add(details);
                            adapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                    filteredUserList.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}