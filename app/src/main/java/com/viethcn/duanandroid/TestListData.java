package com.viethcn.duanandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.Adapters.MainAdapter;
import com.viethcn.duanandroid.Models.MainModel;

public class TestListData extends Fragment {

    RecyclerView recyclerViewMain;
    MainAdapter mainAdapter;
    GridLayoutManager gridLayoutManager;
    FirebaseRecyclerOptions<MainModel> options;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test_list_data, container, false);

        recyclerViewMain = view.findViewById(R.id.recyclerMain);

        gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 cột

        options = new FirebaseRecyclerOptions.Builder<MainModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Product"), MainModel.class)
                .build();


        mainAdapter = new MainAdapter(options);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewMain.setLayoutManager(gridLayoutManager);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
