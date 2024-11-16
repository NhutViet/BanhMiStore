package com.viethcn.duanandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private EditText nameField, emailField, genderField, phoneField;
    private Button editButton, saveButton;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize fields and buttons
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        genderField = findViewById(R.id.genderField);
        phoneField = findViewById(R.id.phoneField);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);

        // Set fields to be non-editable initially
        setFieldsEditable(false);

        // Edit button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditing = true;
                setFieldsEditable(true);
                saveButton.setEnabled(true); // Enable the save button
                Toast.makeText(Profile.this, "Bạn có thể chỉnh sửa hồ sơ", Toast.LENGTH_SHORT).show();
            }
        });

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    // Save profile changes
                    Toast.makeText(Profile.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    setFieldsEditable(false); // Make fields non-editable again
                    saveButton.setEnabled(false); // Disable the save button again
                    isEditing = false; // Reset editing state
                }
            }
        });
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

