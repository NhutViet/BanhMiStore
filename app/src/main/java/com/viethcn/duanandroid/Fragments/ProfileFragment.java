package com.viethcn.duanandroid.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.viethcn.duanandroid.R;

public class ProfileFragment extends Fragment {

    // View components
    private EditText nameField, emailField, genderField, phoneField;
    private Button editButton, saveButton;
    private ImageView profileImage;

    private boolean isEditing = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        // Initialize views
        nameField = view.findViewById(R.id.nameField);
        emailField = view.findViewById(R.id.emailField);
        genderField = view.findViewById(R.id.genderField);
        phoneField = view.findViewById(R.id.phoneField);
        editButton = view.findViewById(R.id.editButton);
        saveButton = view.findViewById(R.id.saveButton);
        profileImage = view.findViewById(R.id.profileImage);

        // Disable save button initially
        saveButton.setEnabled(false);

        // Load Google account info
        loadGoogleAccountInfo();

        // Edit button click listener
        editButton.setOnClickListener(v -> {
            isEditing = true;
            setFieldsEditable(true);
            saveButton.setEnabled(true);
            Toast.makeText(getContext(), "Bạn có thể chỉnh sửa hồ sơ", Toast.LENGTH_SHORT).show();
        });

        // Save button click listener
        saveButton.setOnClickListener(v -> {
            if (isEditing) {
                // Save profile changes
                Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                setFieldsEditable(false);
                saveButton.setEnabled(false);
                isEditing = false;
            }
        });

        return view;
    }

    private void loadGoogleAccountInfo() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null) {
            // Get account info
            String email = account.getEmail();
            String name = account.getDisplayName();
            Uri photo = account.getPhotoUrl();

            // Display info in fields
            emailField.setText(email);
            nameField.setText(name);

            // Load profile image using Glide
            if (photo != null) {
                Glide.with(this).load(photo).into(profileImage);
            }
        } else {
            Toast.makeText(getContext(), "Không tìm thấy thông tin tài khoản Google", Toast.LENGTH_SHORT).show();
        }
    }

    // Enable or disable editing of fields
    private void setFieldsEditable(boolean isEditable) {
        nameField.setFocusableInTouchMode(isEditable);
        emailField.setFocusableInTouchMode(isEditable);
        genderField.setFocusableInTouchMode(isEditable);
        phoneField.setFocusableInTouchMode(isEditable);
    }
}
