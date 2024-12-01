package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;
import com.viethcn.duanandroid.Repositories.MainModelRepository;

public class DetalProductFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout của Fragment
        View view = inflater.inflate(R.layout.fragment_detal_product, container, false);

        // Ánh xạ View
        TextView txtName = view.findViewById(R.id.txtTensp);
        TextView txtPrice = view.findViewById(R.id.txtGiasp);
        TextView txtMotaSp = view.findViewById(R.id.txtMotaSp);
        ImageView btnExit = view.findViewById(R.id.btnExit);
        Button btnThemGioHang = view.findViewById(R.id.btnThemGioHang);
        ImageView imgProduct = view.findViewById(R.id.imgChiTiet);

        // Layout gốc của Fragment
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Trả về true để chặn tất cả sự kiện touch
                return true;
            }
        });

        // Nhận dữ liệu từ Bundle
        if (getArguments() != null) {
            String name = getArguments().getString("name", "Không xác định");
            String price = getArguments().getString("price", "0 VNĐ");
            String img = getArguments().getString("img", "");
            String moTa = getArguments().getString("description", "Không có mô tả");

            // Gán dữ liệu cho View
            txtName.setText(name);
            txtPrice.setText(price + " VNĐ");
            txtMotaSp.setText(moTa);

            Glide.with(this)
                    .load(img)
                    .placeholder(R.drawable.erroimage)
                    .into(imgProduct);
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ViewModel của giỏ hàng
                MainModelRepository cartViewModel = new ViewModelProvider(requireActivity()).get(MainModelRepository.class);

                if (getArguments() != null) {
                    String name = getArguments().getString("name", "Không xác định");
                    String price = getArguments().getString("price", "0 VNĐ");
                    String img = getArguments().getString("img", "");
                    String moTa = getArguments().getString("description", "Không có mô tả");

                    // Tạo đối tượng Product
                    MainModel product = new MainModel(name, price, img, moTa);

                    // Thêm sản phẩm vào ViewModel
                    cartViewModel.addItem(product);

                    // Thông báo cho người dùng
                    Toast.makeText(requireContext(), name + " đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Không thể thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }
}
