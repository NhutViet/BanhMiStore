package com.viethcn.duanandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.viethcn.duanandroid.R;

public class ProfileFragment extends Fragment {

     EditText nameField, emailField, genderField, phoneField;
     Button editButton, saveButton;
     ImageView profileImage;
     boolean isEditing = false;

     FirebaseAuth firebaseAuth;

     Context context;

    public ProfileFragment(Context context){this.context = context;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);

        if (account != null) {

            String email = account.getEmail();
            String name = account.getDisplayName();
            Uri photo = account.getPhotoUrl();

            emailField.setText(email);
            nameField.setText(name);

            Glide.with(this).load(photo).into(profileImage);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        nameField = view.findViewById(R.id.nameField);
        emailField = view.findViewById(R.id.emailField);
        genderField = view.findViewById(R.id.genderField);
        phoneField = view.findViewById(R.id.phoneField);
        editButton = view.findViewById(R.id.editButton);
        saveButton = view.findViewById(R.id.saveButton);
        profileImage = view.findViewById(R.id.profileImage);

        editButton.setOnClickListener( v -> {
            isEditing = true;
            setFieldsEditable(true);
            saveButton.setEnabled(true); // Enable the save button
            Toast.makeText(context, "Bạn có thể chỉnh sửa hồ sơ", Toast.LENGTH_SHORT).show();
        });

        // Save button click listener
        saveButton.setOnClickListener( v -> {
            if (isEditing) {
                // Save profile changes
                Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                setFieldsEditable(false); // Make fields non-editable again
                saveButton.setEnabled(false); // Disable the save button again
                isEditing = false; // Reset editing state
            }
        });

        return view;
    }

    // Method to toggle editability of fields
    private void setFieldsEditable(boolean isEditable) {
        nameField.setFocusable(isEditable);
        nameField.setFocusableInTouchMode(isEditable);
        nameField.setClickable(isEditable);

        emailField.setFocusable(isEditable);
        emailField.setFocusableInTouchMode(isEditable);
        emailField.setClickable(isEditable);

        genderField.setFocusable(isEditable);
        genderField.setFocusableInTouchMode(isEditable);
        genderField.setClickable(isEditable);

        phoneField.setFocusable(isEditable);
        phoneField.setFocusableInTouchMode(isEditable);
        phoneField.setClickable(isEditable);
    }
}

