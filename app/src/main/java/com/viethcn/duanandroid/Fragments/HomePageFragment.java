package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viethcn.duanandroid.Adapters.ProductAdapter;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {

    RecyclerView productRcv;
    List<MainModel> mList;
    ImageSlider imgSlider;
    TextView txtXemThem;
    List<SlideModel> imgList = new ArrayList<>();
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewHolder = inflater.inflate(R.layout.fragment_home_page, container, false);
        productRcv = viewHolder.findViewById(R.id.productRcV);
        txtXemThem = viewHolder.findViewById(R.id.txtXemThem);
        fetchData();

        ProductAdapter adapter = new ProductAdapter(getContext(), mList);
        productRcv.setAdapter(adapter);
        productRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        imgSlider = viewHolder.findViewById(R.id.image_slider);

        txtXemThem.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainViewHomePage, new MenuBanhMiFragment())
                .commit());

        setImgSlider();

        return viewHolder;

    }
    private void fetchData() {
        ref = FirebaseDatabase.getInstance().getReference("Product");
        mList = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Định dạng số cho giá trị giá (price)
                NumberFormat numberFormat = new DecimalFormat("#,###");
                mList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới

                // Duyệt qua tất cả các sản phẩm trong cơ sở dữ liệu
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String img = dataSnapshot.child("img").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);

                    // Xử lý giá trị price: nếu có giá trị hợp lệ thì định dạng lại, nếu không thì đặt giá mặc định là "0"
                    double priceValue = 0.0;
                    if (price != null && !price.isEmpty()) {
                        try {
                            priceValue = Double.parseDouble(price);
                        } catch (NumberFormatException e) {
                            priceValue = 0.0; // Nếu lỗi khi chuyển giá trị, dùng giá trị mặc định là 0
                        }
                    }
                    // Định dạng giá trị price
                    String formattedPrice = numberFormat.format(priceValue);

                    // Thêm vào danh sách mList với các giá trị đã xử lý
                    mList.add(new MainModel(name, formattedPrice, img));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("///===ERROR", error.getMessage());
            }
        });
    }


    private void setImgSlider(){
        imgList.add(new SlideModel(R.drawable.banner01, ScaleTypes.FIT));
        imgList.add(new SlideModel(R.drawable.banner02, ScaleTypes.FIT));
        imgSlider.setImageList(imgList);
    }
}