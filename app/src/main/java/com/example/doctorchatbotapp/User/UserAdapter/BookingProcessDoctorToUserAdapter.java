package com.example.doctorchatbotapp.User.UserAdapter;

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

public class BookingProcessDoctorToUserAdapter extends RecyclerView.Adapter<BookingProcessDoctorToUserAdapter.TeacherStudentListViewHolder> {
    private ArrayList<AddPatientDetailToRealtym> mList;
    private Context context;

    public BookingProcessDoctorToUserAdapter(ArrayList<AddPatientDetailToRealtym> mList, Context context) {
        this.mList = mList;
        this.context = context;

    }

    @NonNull
    @Override
    public TeacherStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.req_process_doctor_to_user_layout, parent, false);
        return new TeacherStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentListViewHolder holder, int position) {
        final AddPatientDetailToRealtym data = mList.get(position);

        Glide.with(context).load(data.getImageurl())
                .into(holder.trainnerImg);
        holder.trainnername.setText(data.getUsername());



    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TeacherStudentListViewHolder extends RecyclerView.ViewHolder {
        ImageView trainnerImg;
        TextView trainnername;


        public TeacherStudentListViewHolder(@NonNull View itemView) {
            super(itemView);

            trainnerImg = itemView.findViewById(R.id.trainnerimageID);
            trainnername = itemView.findViewById(R.id.trainnerNameText);


        }
    }
}
