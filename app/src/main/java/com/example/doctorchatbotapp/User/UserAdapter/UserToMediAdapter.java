package com.example.doctorchatbotapp.User.UserAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.MedicaneDetailsScreenActivity;
import com.example.doctorchatbotapp.User.UserViewMedicalInfoActivity;

import java.util.ArrayList;

public class UserToMediAdapter extends RecyclerView.Adapter<UserToMediAdapter.TeacherStudentListViewHolder> {
    private ArrayList<AddProdcutDetails> mList;
    private Context context;

    public UserToMediAdapter(ArrayList<AddProdcutDetails> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_medi_layout, parent, false);
        return new TeacherStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentListViewHolder holder, int position) {
        final AddProdcutDetails data = mList.get(position);

        Glide.with(context).load(data.getProductimage())
                .into(holder.userImg);
        holder.username.setText(data.getProductname());
        holder.tvprice.setText(data.getProductprice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj = new Intent(context, UserViewMedicalInfoActivity.class);
                obj.putExtra("productID",data.getProductID());
                obj.putExtra("productimage",data.getProductID());
                obj.putExtra("productname",data.getProductID());
                obj.putExtra("productprice",data.getProductprice());
                obj.putExtra("ownerID",data.getUserID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TeacherStudentListViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView username,tvprice;

        public TeacherStudentListViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.userimageID);
            username = itemView.findViewById(R.id.userameText);
            tvprice= itemView.findViewById(R.id.textView7);

        }
    }
}
