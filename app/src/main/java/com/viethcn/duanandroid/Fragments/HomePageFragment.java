package com.viethcn.duanandroid.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.viethcn.duanandroid.Adapters.ProductAdapter;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.DAO.ProductDAO;
import com.viethcn.duanandroid.R;

import java.util.ArrayList;
import java.util.List;
public class HomePageFragment extends Fragment {

    private List<MainModel> mList;
    private ImageSlider imgSlider;
    private final List<SlideModel> imgList = new ArrayList<>();
    private ProductDAO mDAO;
    private ProductAdapter adapter; // Khai báo adapter

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDAO = ProductDAO.getInstance();
        mList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHolder = inflater.inflate(R.layout.fragment_home_page, container, false);
        RecyclerView productRcv = viewHolder.findViewById(R.id.productRcV);
        TextView txtXemThem = viewHolder.findViewById(R.id.txtXemThem);

        adapter = new ProductAdapter(getContext(), mList);
        productRcv.setAdapter(adapter);
        productRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        imgSlider = viewHolder.findViewById(R.id.image_slider);
        setImgSlider();

        txtXemThem.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainViewHomePage, new MenuBanhMiFragment())
                .commit());

        mDAO.getListProduct(new ProductDAO.ProductCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCallback(List<MainModel> productList) {
                mList.clear();
                mList.addAll(productList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Exception exception) {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    private void setImgSlider() {
        imgList.add(new SlideModel(R.drawable.banner01, ScaleTypes.FIT));
        imgList.add(new SlideModel(R.drawable.banner02, ScaleTypes.FIT));
        imgSlider.setImageList(imgList);
    }
}