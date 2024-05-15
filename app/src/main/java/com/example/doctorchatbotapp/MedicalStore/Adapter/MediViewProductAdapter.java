package com.example.doctorchatbotapp.MedicalStore.Adapter;

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
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.ModelClass.ConfrimCartDetails;


import java.util.ArrayList;

public class MediViewProductAdapter extends RecyclerView.Adapter<MediViewProductAdapter.AdminViewProductViewHolder>{
    private ArrayList<ConfrimCartDetails> list;
    private Context context;

    public MediViewProductAdapter(ArrayList<ConfrimCartDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminViewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_layout,parent,false);
        return new AdminViewProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewProductViewHolder holder, int position) {
        final ConfrimCartDetails data = list.get(position);

        Glide.with(context).load(data.getImage()).into(holder.productImg);
        holder.productName.setText(data.getName());
        holder.productPrice.setText(data.getPrice());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AdminViewProductViewHolder extends RecyclerView.ViewHolder{
        ImageView productImg;
        TextView productName, productPrice;
        public AdminViewProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.enrolledTeacherImg);
            productName = itemView.findViewById(R.id.nameID);
            productPrice = itemView.findViewById(R.id.textView8);

        }
    }
}
