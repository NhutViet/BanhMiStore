package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.viethcn.duanandroid.R;

public class ProfileFragment extends Fragment {

     EditText nameField, emailField, genderField, phoneField;
     Button editButton, saveButton;
     Boolean isEditing = false;

     public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewHolder = inflater.inflate(R.layout.fragment_personal, container, false);

        nameField = viewHolder.findViewById(R.id.nameField);
        emailField = viewHolder.findViewById(R.id.emailField);
        genderField = viewHolder.findViewById(R.id.genderField);
        phoneField = viewHolder.findViewById(R.id.phoneField);
        editButton = viewHolder.findViewById(R.id.editButton);
        saveButton = viewHolder.findViewById(R.id.saveButton);

         // Edit button click listener
        editButton.setOnClickListener( v -> {
            isEditing = true;
            setFieldsEditable(true);
            saveButton.setEnabled(true); // Enable the save button
            Toast.makeText(getContext(), "Bạn có thể chỉnh sửa hồ sơ", Toast.LENGTH_SHORT).show();
        });

        // Save button click listener
        saveButton.setOnClickListener( v -> {
            if (isEditing) {
                // Save profile changes
                Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                setFieldsEditable(false); // Make fields non-editable again
                saveButton.setEnabled(false); // Disable the save button again
                isEditing = false; // Reset editing state
            }
        });

        return viewHolder;
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

