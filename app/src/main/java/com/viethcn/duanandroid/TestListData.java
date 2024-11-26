package com.viethcn.duanandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.Adapters.MainAdapter;
import com.viethcn.duanandroid.Models.MainModel;

public class TestListData extends AppCompatActivity {

    RecyclerView recyclerViewMain;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_data);

        recyclerViewMain = findViewById(R.id.recyclerMain);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 cột
        recyclerViewMain.setLayoutManager(gridLayoutManager);

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerViewMain.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
