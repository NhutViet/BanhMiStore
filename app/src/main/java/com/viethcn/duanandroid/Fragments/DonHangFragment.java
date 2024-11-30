package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.Adapters.DonHangAdapter;
import com.viethcn.duanandroid.Models.DonHang;

import com.viethcn.duanandroid.R;

public class DonHangFragment extends Fragment {

    private RecyclerView recyclerView;
    private DonHangAdapter donHangAdapter;
    FirebaseRecyclerOptions<DonHang> options;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewDonHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo FirebaseRecyclerOptions
        FirebaseRecyclerOptions<DonHang> options =
                new FirebaseRecyclerOptions.Builder<DonHang>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), DonHang.class)
                        .build();

        // Khởi tạo adapter với FirebaseRecyclerOptions
        donHangAdapter = new DonHangAdapter(options);
        recyclerView.setAdapter(donHangAdapter);

        return view; // Trả về view đã setup xong
    }

    @Override
    public void onStart() {
        super.onStart();
        // Bắt đầu nghe dữ liệu từ Firebase
        donHangAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Dừng nghe dữ liệu khi fragment dừng hoạt động
        donHangAdapter.stopListening();
    }
}
