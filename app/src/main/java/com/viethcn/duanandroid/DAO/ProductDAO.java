package com.viethcn.duanandroid.DAO;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viethcn.duanandroid.Models.MainModel;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static ProductDAO instance;
    private final DatabaseReference dbRef;

    private ProductDAO() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Product");
    }

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    public void getListProduct(final ProductCallback callback) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<MainModel> productList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainModel product = snapshot.getValue(MainModel.class);
                    productList.add(product);
                }
                callback.onCallback(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
    }

    // Callback interface
    public interface ProductCallback {
        void onCallback(List<MainModel> productList);
        void onError(Exception exception);
    }

}