package com.viethcn.duanandroid.DAO;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viethcn.duanandroid.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static DatabaseReference mDatabase;

    public static List<Product> getListProduct() {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        List<Product> mList = new ArrayList<>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    mList.add(product);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mList;
    }
}
