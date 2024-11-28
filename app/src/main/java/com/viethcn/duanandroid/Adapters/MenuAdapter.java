package com.viethcn.duanandroid.Adapters;

import android.app.AlertDialog;
import android.content.Context;
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
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.Models.Product;
import com.viethcn.duanandroid.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuAdapter extends FirebaseRecyclerAdapter<MainModel, MenuAdapter.myViewHolder> {
    ArrayList<Product> list;
    Context context;

    public MenuAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options, ArrayList<Product> list, Context context) {
        super(options);
        this.list = list;
        this.context = context;
    }

    public MenuAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        NumberFormat numberFormat = new DecimalFormat("#,###");
        double myNumber = 0.0;

        // Kiểm tra và định dạng giá
        if (model.getPrice() != null && !model.getPrice().isEmpty()) {
            try {
                myNumber = Double.parseDouble(model.getPrice());
            } catch (NumberFormatException e) {
                myNumber = 0.0;
            }
        }
        String formatterNumber = numberFormat.format(myNumber);
        holder.name.setText( (model.getName() != null ? model.getName() : "Không xác định"));
        holder.price.setText(formatterNumber + " VNĐ");

        Glide.with(holder.img.getContext())
                .load(model.getImg())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(v -> {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                    .setContentHolder(new ViewHolder(R.layout.update_product))
                    .setExpanded(true, 1200)
                    .create();

            View view = dialogPlus.getHolderView();
            EditText edtNamePR = view.findViewById(R.id.edtNamePR);
            EditText edtPricePR = view.findViewById(R.id.edtPricePR);
            EditText edtImgPR = view.findViewById(R.id.edtImgPR);
            Button btnUpdatePR = view.findViewById(R.id.btnUpdate);

            edtNamePR.setText(model.getName());
            edtPricePR.setText(model.getPrice());
            edtImgPR.setText(model.getImg());

            dialogPlus.show();

            btnUpdatePR.setOnClickListener(view1 -> {
                if (edtNamePR.getText().toString().isEmpty() || edtPricePR.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("name", edtNamePR.getText().toString());
                map.put("price", edtPricePR.getText().toString());
                map.put("img", edtImgPR.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Product")
                        .child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(holder.name.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(holder.name.getContext(), "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        });
            });
        });

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
            builder.setTitle("Xác nhận xoá");
            builder.setMessage("Bạn có chắc chắn muốn xoá sản phẩm này không?");
            builder.setPositiveButton("Xoá", (dialog, which) -> {
                FirebaseDatabase.getInstance().getReference().child("Product")
                        .child(getRef(position).getKey()).removeValue()
                        .addOnSuccessListener(unused -> Toast.makeText(holder.name.getContext(), "Xoá thành công!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(holder.name.getContext(), "Xoá thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    // Đảm bảo RecyclerView luôn cập nhật khi dữ liệu thay đổi
    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thucdon, parent, false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, price;
        ImageView btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.menuItemImage);
            name = itemView.findViewById(R.id.menuItemName);
            price = itemView.findViewById(R.id.menuItemPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
