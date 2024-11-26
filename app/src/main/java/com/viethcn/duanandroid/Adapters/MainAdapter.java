package com.viethcn.duanandroid.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.viethcn.duanandroid.CartActivity;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull MainModel mainModel) {
        myViewHolder.name.setText("Tên: " + mainModel.getName());
        myViewHolder.price.setText("Giá: "+ mainModel.getPrice());



        Glide.with(myViewHolder.img.getContext())
                .load(mainModel.getImg())
                .placeholder(com.google.android.gms.auth.api.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(myViewHolder.img);

        myViewHolder.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CartActivity.class);
                intent.putExtra( "name", mainModel.getName());
                intent.putExtra("price", mainModel.getPrice());
                intent.putExtra("img", mainModel.getImg());


                v.getContext().startActivity(intent);

            }
        });

    }




    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thucdon, parent, false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name, price;

        Button btnBuyNow;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.menuItemImage);
            name = itemView.findViewById(R.id.menuItemName);
            price = itemView.findViewById(R.id.menuItemPrice);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
        }
    }

}
