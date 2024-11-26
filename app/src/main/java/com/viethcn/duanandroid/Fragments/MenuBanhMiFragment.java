package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.Adapters.MenuAdapter;

import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

public class MenuBanhMiFragment extends Fragment {

    RecyclerView recyclerViewMain;
    MenuAdapter mainAdapter;
    GridLayoutManager gridLayoutManager;
    FirebaseRecyclerOptions<MainModel> options;
    FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.activity_test_list_data, container, false);

        recyclerViewMain = view.findViewById(R.id.recyclerMain);
        fabAdd = view.findViewById(R.id.fabAdd);

        setMenuData();

        fabAdd.setOnClickListener(v -> {
            Fragment insertProductFragment = new InsrtPrdctFraqment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainViewHomePage, insertProductFragment)
                    .addToBackStack(null)
                    .commit();
        });


        return view;
    }

    private void setMenuData(){
        gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 columns
        recyclerViewMain.setLayoutManager(gridLayoutManager);

        options = new FirebaseRecyclerOptions.Builder<MainModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Product"), MainModel.class)
                .build();

        mainAdapter = new MenuAdapter(options);
        recyclerViewMain.setAdapter(mainAdapter);

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
