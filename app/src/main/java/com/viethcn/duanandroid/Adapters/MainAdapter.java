package com.viethcn.duanandroid.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

//        myViewHolder.btnBuyNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), CartActivity.class);
//                intent.putExtra( "name", mainModel.getName());
//                intent.putExtra("price", mainModel.getPrice());
//                intent.putExtra("img", mainModel.getImg());
//
//
//                v.getContext().startActivity(intent);
//
//            }
//        });

        myViewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(myViewHolder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_product))
                        .setExpanded(true, 1200)
                        .create();



                View view = dialogPlus.getHolderView();
                EditText edtNamePR = view.findViewById(R.id.edtNamePR);
                EditText edtPricePR = view.findViewById(R.id.edtPricePR);
                EditText edtImgPR = view.findViewById(R.id.edtImgPR);
                Button btnUpdatePR = view.findViewById(R.id.btnUpdate);

                edtNamePR.setText(mainModel.getName());
                edtPricePR.setText(mainModel.getPrice());
                edtImgPR.setText(mainModel.getImg());

                dialogPlus.show();


                btnUpdatePR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", edtNamePR.getText().toString());
                        map.put("price", edtPricePR.getText().toString());
                        map.put("img", edtImgPR.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Product")
                                .child(getRef(i).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(myViewHolder.name.getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(myViewHolder.name.getContext(), "Sửa Thất bại", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });
                    }
                });
            }

        });


        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myViewHolder.name.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xoá sản phẩm này không?");
                builder.setMessage("Xoá dữ liệu không thể quay lại");
                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Product")
                                        .child(getRef(i).getKey()).removeValue();
                        Toast.makeText(myViewHolder.name.getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(myViewHolder.name.getContext(), "Bạn đã huỷ thao tác", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.show();
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
        ImageView  btnEdit, btnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.menuItemImage);
            name = itemView.findViewById(R.id.menuItemName);
            price = itemView.findViewById(R.id.menuItemPrice);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
