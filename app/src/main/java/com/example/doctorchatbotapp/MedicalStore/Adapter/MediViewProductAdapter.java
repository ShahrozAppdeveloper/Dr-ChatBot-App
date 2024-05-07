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


import java.util.ArrayList;

public class MediViewProductAdapter extends RecyclerView.Adapter<MediViewProductAdapter.AdminViewProductViewHolder>{
    private ArrayList<AddProdcutDetails> list;
    private Context context;

    public MediViewProductAdapter(ArrayList<AddProdcutDetails> list, Context context) {
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
        final AddProdcutDetails data = list.get(position);

        Glide.with(context).load(data.getProductimage()).into(holder.productImg);
        holder.productName.setText(data.getProductname());
        holder.productPrice.setText(data.getProductprice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, AdminUpdateProductActivity.class)
//                        .putExtra("productList",list)
//                        .putExtra("position", position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AdminViewProductViewHolder extends RecyclerView.ViewHolder{
        ImageView productImg;
        TextView productName, productPrice, productQuantity;
        public AdminViewProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.enrolledTeacherImg);
            productName = itemView.findViewById(R.id.enrolledTeacherName);
            productPrice = itemView.findViewById(R.id.enrolledTeacherSchoolId);

        }
    }
}
