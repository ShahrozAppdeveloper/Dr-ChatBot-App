package com.example.doctorchatbotapp.Admin.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;

import com.example.doctorchatbotapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminDoctorAdapter extends RecyclerView.Adapter<AdminDoctorAdapter.TeacherStudentListViewHolder> {
    private ArrayList<AddPatientDetailToRealtym> mList;
    private Context context;
    DatabaseReference reftrainneraccept;
    DatabaseReference refuseraccept;
    FirebaseDatabase database;


    public AdminDoctorAdapter(ArrayList<AddPatientDetailToRealtym> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_doc_layout, parent, false);
        return new TeacherStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentListViewHolder holder, int position) {
        final AddPatientDetailToRealtym data = mList.get(position);

        Glide.with(context).load(data.getImageurl())
                .into(holder.trainnerImg);
        holder.trainnername.setText(data.getUsername());
        // creating the instance
        database = FirebaseDatabase.getInstance();
        holder.Imagetick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String trainnerID = data.getUserid();
               int position = holder.getAdapterPosition();
                proveTrainnerRequest(trainnerID,position);
            }
        });
        holder.imagefalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trainnerID = data.getUserid();
                int position = holder.getAdapterPosition();

                removeTrainnerRequest(trainnerID,position);
            }
        });


    }
    private void proveTrainnerRequest(String trainnerID,int position){
        reftrainneraccept = database.getReference("DoctorProfile")
                .child("Details")
                .child(trainnerID);
        reftrainneraccept
                .child("provedstatus")
                .setValue("proved").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mList.remove(position);
                            // Notify the adapter about the item removal
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Proved", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void removeTrainnerRequest(String trainnerID,int position){
        reftrainneraccept = database.getReference("DoctorProfile")
                .child("Details")
                .child(trainnerID);
        reftrainneraccept
                .child("provedstatus")
                .setValue("delete").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mList.remove(position);
                            // Notify the adapter about the item removal
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Request delete", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TeacherStudentListViewHolder extends RecyclerView.ViewHolder {
        ImageView trainnerImg,Imagetick,imagefalse;
        TextView trainnername;

        public TeacherStudentListViewHolder(@NonNull View itemView) {
            super(itemView);

            trainnerImg = itemView.findViewById(R.id.trainnerimageID);
            trainnername = itemView.findViewById(R.id.trainnerNameText);
            Imagetick = itemView.findViewById(R.id.tickID);
            imagefalse = itemView.findViewById(R.id.falseID);

        }
    }
}
