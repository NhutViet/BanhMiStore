package com.viethcn.duanandroid.Adapters;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.viethcn.duanandroid.Fragments.DetalProductFragment;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;
import com.viethcn.duanandroid.Repositories.MainModelRepository;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuAdapter extends FirebaseRecyclerAdapter<MainModel, MenuAdapter.myViewHolder> {

    private FragmentActivity context;

    public MenuAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options, FragmentActivity activity) {
        super(options);
        this.context = activity;
    }

    public MenuAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        SharedPreferences sharedPreferences = context != null
                ? context.getSharedPreferences("thongtin", Context.MODE_PRIVATE)
                : holder.itemView.getContext().getSharedPreferences("thongtin", Context.MODE_PRIVATE);

        String loai = sharedPreferences.getString("rule", "");

        // Kiểm tra vai trò và ẩn nút nếu không phải admin
        if (!"admin".equals(loai)) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.GONE);
        }




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
                .placeholder(R.drawable.erroimage)
                .circleCrop()
                .error(R.drawable.erroimage)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(v -> {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                    .setContentHolder(new ViewHolder(R.layout.update_product))
                    .setExpanded(true, 1600)
                    .setContentBackgroundResource(R.color.MauNen)
                    .create();

            View view = dialogPlus.getHolderView();
            EditText edtNamePR = view.findViewById(R.id.edtNamePR);
            EditText edtPricePR = view.findViewById(R.id.edtPricePR);
            EditText edtImgPR = view.findViewById(R.id.edtImgPR);
            EditText edtDescribe = view.findViewById(R.id.edtDescribe);
            Button btnUpdatePR = view.findViewById(R.id.btnUpdate);

            edtNamePR.setText(model.getName());
            edtPricePR.setText(model.getPrice());
            edtDescribe.setText(model.getDescription());
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
                map.put("description", edtDescribe.getText().toString());
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

        holder.btnBuy.setOnClickListener(v -> {
            if (context != null) {
                FragmentActivity activity =  context;
                MainModelRepository viewModel = new ViewModelProvider(activity).get(MainModelRepository.class);

                viewModel.addItem(model);

                Toast.makeText(context, model.getName() + " đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Không thể thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            }
        });


        holder.ItemPR.setOnClickListener(v -> {
            DetalProductFragment detailFragment = new DetalProductFragment();

            // Truyền dữ liệu sản phẩm qua Bundle
            Bundle bundle = new Bundle();
            bundle.putString("name", model.getName());
            bundle.putString("price", model.getPrice());
            bundle.putString("img", model.getImg());
            bundle.putString("description", model.getDescription());
            detailFragment.setArguments(bundle);

            // Chuyển đổi Fragment
            if (context != null) {
                context.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.MenuMainLayout_Container, detailFragment) // `fragment_container` là ID của container trong Activity
                        .addToBackStack(null) // Để người dùng có thể quay lại
                        .commit();
            }
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
        Button btnBuy;
        LinearLayout ItemPR;

        View MenuMainLayout_Container;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.menuItemImage);
            name = itemView.findViewById(R.id.menuItemName);
            price = itemView.findViewById(R.id.menuItemPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnBuy = itemView.findViewById(R.id.btnBuy);
            ItemPR = itemView.findViewById(R.id.ItemPR);
            MenuMainLayout_Container = itemView.findViewById(R.id.MenuMainLayout_Container);
        }

    }
}
