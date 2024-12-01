package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.viethcn.duanandroid.Adapters.DonHangAdapter;
import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class DonHangFragment extends Fragment {
    private RecyclerView recyclerView;
    private DonHangAdapter adapter;
    private List<DonHang> donHangList;
    private List<MainModel> productList;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        // Kết nối Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Recipts");
        productList=new ArrayList<>();
        donHangList= new ArrayList<>();
        loadDonHangData();

        return view;
    }


    private void loadDonHangData() {
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot receiptSnapshot : snapshot.getChildren()) {
                    // Truy cập từng trường trong mỗi đơn hàng
                    String owner = receiptSnapshot.child("owner").getValue(String.class);
                    String address = receiptSnapshot.child("address").getValue(String.class);
                    String phone = receiptSnapshot.child("phone").getValue(String.class);
                    String note = receiptSnapshot.child("note").getValue(String.class);
                    Double total = receiptSnapshot.child("total").getValue(Double.class);

                    DataSnapshot listProductSnapshot = receiptSnapshot.child("listProduct");


                    for (DataSnapshot productSnapshot : listProductSnapshot.getChildren()) {
                        MainModel product = productSnapshot.getValue(MainModel.class);
                        if (product != null) {
                            productList.add(product);
                        }
                    }
                    if (note.isEmpty()) {
                        donHangList.add(new DonHang(address,owner,phone,total,productList));
                    }else{
                        donHangList.add(new DonHang(address,owner,phone,total,note,productList));
                    }
                }
                adapter = new DonHangAdapter(getContext(), donHangList);
                recyclerView.setAdapter(adapter);
            }

                    @Override
                    public void onCancelled (@NonNull DatabaseError error){
                        Log.e("FirebaseData", "Database error: " + error.getMessage());
                    }
                });
        }
    }
