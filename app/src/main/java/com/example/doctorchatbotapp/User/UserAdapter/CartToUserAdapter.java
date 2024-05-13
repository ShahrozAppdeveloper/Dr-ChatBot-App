package com.example.doctorchatbotapp.User.UserAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.ModelClass.MemberBookingDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.ModelClass.AddItemToCartDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartToUserAdapter extends RecyclerView.Adapter<CartToUserAdapter.TeacherStudentListViewHolder> {
    private ArrayList<AddItemToCartDetails> mList;
    private Context context;

    String currentuserID;


    public CartToUserAdapter(ArrayList<AddItemToCartDetails> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart_details_layout, parent, false);
        return new TeacherStudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentListViewHolder holder, int position) {
        final AddItemToCartDetails data = mList.get(position);

        Glide.with(context).load(data.getProductImage())
                .into(holder.trainnerImg);
        holder.trainnername.setText(data.getProductname());
        holder.tvprice.setText(data.getProductprice());

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TeacherStudentListViewHolder extends RecyclerView.ViewHolder {
        ImageView trainnerImg;
        TextView trainnername,tvprice;
        CardView btnbook;

        public TeacherStudentListViewHolder(@NonNull View itemView) {
            super(itemView);

            trainnerImg = itemView.findViewById(R.id.trainnerimageID);
            trainnername = itemView.findViewById(R.id.trainnerNameText);
            tvprice = itemView.findViewById(R.id.priceID);
            btnbook = itemView.findViewById(R.id.btnbookID);

        }
    }
}
