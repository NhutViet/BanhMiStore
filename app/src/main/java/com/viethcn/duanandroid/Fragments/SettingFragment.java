package com.viethcn.duanandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.viethcn.duanandroid.LoginActivity;
import com.viethcn.duanandroid.R;

public class SettingFragment extends Fragment {
    Button logOutSettingFragment;
    private TextView nameField, emailField;
    private ImageView profileImage;
    TextView tvDonhang;

    public SettingFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        logOutSettingFragment = view.findViewById(R.id.logOutSettingFragment);
        nameField = view.findViewById(R.id.nameField);
        emailField = view.findViewById(R.id.emailField);
        profileImage = view.findViewById(R.id.profileImage);
        tvDonhang = view.findViewById(R.id.tvdonHang);

        tvDonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainViewHomePage,new DonHangFragment()).commit();
            }
        });

        loadGoogleAccountInfo();

        logOutSettingFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        
        return view;
    }

    private void loadGoogleAccountInfo() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String loginType = sharedPreferences.getString("rule", "google");

        if ("google".equals(loginType)) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
            if (account != null) {
                // Lấy thông tin tài khoản Google
                String email = account.getEmail();
                String name = account.getDisplayName();
                Uri photo = account.getPhotoUrl();

                emailField.setText(email);
                nameField.setText(name);

                if (photo != null) {
                    Glide.with(this).load(photo).into(profileImage);
                }
            } else {
                Toast.makeText(getContext(), "Không tìm thấy thông tin tài khoản Google", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Không phải tài khoản Google", Toast.LENGTH_SHORT).show();
        }
    }


}