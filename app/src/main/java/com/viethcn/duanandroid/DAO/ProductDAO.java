package com.viethcn.duanandroid.DAO;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.viethcn.duanandroid.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private FirebaseFirestore db;

    public Object getInstance() {
        return db = FirebaseFirestore.getInstance();
    }

    public List<Product> getAllProduct(){

        CollectionReference productsRef = db.collection("Products");
        List<Product> mList = new ArrayList<>();




        return mList;
    }
}
