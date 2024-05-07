package com.example.doctorchatbotapp.User.UserAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.UserChatActivity;


import java.util.ArrayList;

public class BookedUserToDoctorAdapter extends RecyclerView.Adapter<BookedUserToDoctorAdapter.TeacherStudentListViewHolder> {
    private ArrayList<AddPatientDetailToRealtym> mList;
    private Context context;

    public BookedUserToDoctorAdapter(ArrayList<AddPatientDetailToRealtym> mList, Context context) {
        this.mList = mList;
        this.context = context;

    }

    @NonNull
    @Override
    public TeacherStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_user_to_doctor_layout, parent, false);
        return new TeacherStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentListViewHolder holder, int position) {
        final AddPatientDetailToRealtym data = mList.get(position);

        Glide.with(context).load(data.getImageurl())
                .into(holder.trainnerImg);
        holder.trainnername.setText(data.getUsername());

        holder.btnmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserChatActivity.class)
                        .putExtra("trainnername",data.getUsername())
                        .putExtra("trainnerImage",data.getImageurl())
                        .putExtra("trainnerId",data.getUserid()));
            }
        });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TeacherStudentListViewHolder extends RecyclerView.ViewHolder {
        ImageView trainnerImg;
        TextView trainnername;
        CardView btnmsg;


        public TeacherStudentListViewHolder(@NonNull View itemView) {
            super(itemView);

            trainnerImg = itemView.findViewById(R.id.trainnerimageID);
            trainnername = itemView.findViewById(R.id.userNameText);
            btnmsg = itemView.findViewById(R.id.btnmsgID);


        }
    }
}
