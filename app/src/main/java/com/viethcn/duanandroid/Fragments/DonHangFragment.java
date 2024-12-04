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
import com.viethcn.duanandroid.LoginActivity;
import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DonHangFragment extends Fragment {
    private RecyclerView recyclerView;
    private DonHangAdapter adapter;
    private List<DonHang> mListDonHang;
    private Query query;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Kết nối Firebase
        SharedPreferences tokenRef = requireActivity().getSharedPreferences("userID", LoginActivity.MODE_PRIVATE);
        String userId = tokenRef.getString("userID", "");

        databaseReference = FirebaseDatabase.getInstance().getReference("Recipts");
        query = databaseReference.orderByChild("userId").equalTo(userId);

        mListDonHang = new ArrayList<>();
        loadDonHangData();

        adapter = new DonHangAdapter(requireActivity(), mListDonHang);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void loadDonHangData() {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        String reciptsID = item.child("reciptsID").getValue(String.class);
                        String address = item.child("address").getValue(String.class);
                        String owner = item.child("owner").getValue(String.class);
                        String phone = item.child("phone").getValue(String.class);
                        Double total = item.child("total").getValue(Double.class);
                        String note = item.child("note").getValue(String.class);
                        String status = item.child("status").getValue(String.class);
                        Double createdAt = item.child("createdAt").getValue(Double.class);

                        GenericTypeIndicator<List<MainModel>> typeIndicator = new GenericTypeIndicator<List<MainModel>>() {};
                        List<MainModel> listProduct = item.child("listProduct").getValue(typeIndicator);

                        DonHang donHang = new DonHang(reciptsID ,address, owner, phone, total, note, listProduct, status, createdAt);
                        mListDonHang.add(donHang);
                    }
                    mListDonHang.sort( (r1, r2) -> Double.compare(r2.getCreateAt(), r1.getCreateAt()));

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Database error: " + error.getMessage());
            }
        });
    }
}
