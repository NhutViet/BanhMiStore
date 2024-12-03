package com.viethcn.duanandroid.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import com.google.firebase.database.Query;
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
    private List<DonHang> mListDonHang;
    private Query query;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Kết nối Firebase
        SharedPreferences tokenRef = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        String id = tokenRef.getString("token", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Recipts");
        query = databaseReference.orderByChild("id").equalTo(id);

        mListDonHang = new ArrayList<>();
        loadDonHangData();

        return view;
    }


    private void loadDonHangData() {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        String address = item.child("address").getValue(String.class);
                        String owner = item.child("owner").getValue(String.class);
                        String phone = item.child("phone").getValue(String.class);
                        Double total = item.child("total").getValue(Double.class);
                        String note = item.child("note").getValue(String.class);

                        GenericTypeIndicator<List<MainModel>> typeIndicator = new GenericTypeIndicator<List<MainModel>>() {};
                        List<MainModel> listProduct = item.child("listProduct").getValue(typeIndicator);

                        DonHang donHang = new DonHang(address, owner, phone, total, note, listProduct);
                        mListDonHang.add(donHang);
                    }
                }

                adapter = new DonHangAdapter(getContext(), mListDonHang);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                Log.e("FirebaseData", "Database error: " + error.getMessage());
            }
        });
    }
}
