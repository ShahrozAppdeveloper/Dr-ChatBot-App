package com.example.doctorchatbotapp.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;


import java.util.ArrayList;

public class UserToAdminAdapter extends RecyclerView.Adapter<UserToAdminAdapter.TeacherStudentListViewHolder> {
    private ArrayList<AddPatientDetailToRealtym> mList;
    private Context context;

    public UserToAdminAdapter(ArrayList<AddPatientDetailToRealtym> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user_layout, parent, false);
        return new TeacherStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentListViewHolder holder, int position) {
        final AddPatientDetailToRealtym data = mList.get(position);

        Glide.with(context).load(data.getImageurl())
                .into(holder.userImg);
        holder.username.setText(data.getUsername());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TeacherStudentListViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView username;

        public TeacherStudentListViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.userimageID);
            username = itemView.findViewById(R.id.userameText);

        }
    }
}
