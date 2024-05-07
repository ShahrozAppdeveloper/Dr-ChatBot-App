package com.example.doctorchatbotapp.Doctor.Adapter;

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
import com.example.doctorchatbotapp.ModelClass.AddRequserDetailsToDatabase;
import com.example.doctorchatbotapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReqUserToDoctorAdapter extends RecyclerView.Adapter<ReqUserToDoctorAdapter.TeacherStudentListViewHolder> {
    private ArrayList<AddRequserDetailsToDatabase> mList;
    private Context context;
    FirebaseDatabase database;
    DatabaseReference reftrainneraccept;
    DatabaseReference refuseraccept;
    String currentuserID;

    public ReqUserToDoctorAdapter(ArrayList<AddRequserDetailsToDatabase> mList, Context context, String currentuserID) {
        this.mList = mList;
        this.context = context;
        this.currentuserID = currentuserID;

    }

    @NonNull
    @Override
    public TeacherStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.req_rv_user_layout, parent, false);
        return new TeacherStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentListViewHolder holder, int position) {
        final AddRequserDetailsToDatabase data = mList.get(position);

        Glide.with(context).load(data.getImageurl())
                .into(holder.userImg);
        holder.username.setText(data.getUsername());
        // creating the instance
      database = FirebaseDatabase.getInstance();
        holder.tickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memberID = data.getUserID();
                String ReqID = data.getReqID();
                AcceptTrainnerRequest(currentuserID,memberID,ReqID);
            }
        });
        holder.imageFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memberID = data.getUserID();
                String ReqID = data.getReqID();
                removeTrainnerRequest(currentuserID,memberID,ReqID);
            }
        });

    }
    private void AcceptTrainnerRequest(String trainnerID,String userID,String reqID){
        reftrainneraccept = database.getReference("DoctorBooking")
                .child("Info")
                .child(trainnerID).
                 child(reqID);
        reftrainneraccept
                .child("reqstatus")
                .setValue("Accept").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    refuseraccept= database.getReference("MemberBooking")
                            .child("Info")
                            .child(userID).child(reqID);
                    refuseraccept
                            .child("reqstatus")
                            .setValue("Accept").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                notifyDataSetChanged();
                                Toast.makeText(context, "Request Accept", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void removeTrainnerRequest(String trainnerID,String userID,String reqID){
        reftrainneraccept = database.getReference("DoctorBooking")
                .child("Info")
                .child(trainnerID).
                child(reqID);
        reftrainneraccept
                .child("reqstatus")
                .setValue("delete").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            refuseraccept= database.getReference("MemberBooking")
                                    .child("Info")
                                    .child(userID).child(reqID);
                            refuseraccept
                                    .child("reqstatus")
                                    .setValue("delete").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                notifyDataSetChanged();
                                                Toast.makeText(context, "Request Delete", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
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
        ImageView userImg,tickImage,imageFalse;
        TextView username;


        public TeacherStudentListViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.userimageID);
            username = itemView.findViewById(R.id.userNameText);
            tickImage = itemView.findViewById(R.id.tickID);
            imageFalse = itemView.findViewById(R.id.falseID);

        }
    }
}
