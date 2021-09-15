package com.ss.UserListApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ss.UserListApplication.model.User;

public class UserEntryActivity extends AppCompatActivity {

    private Toolbar userEntry_toolbar;
    private TextInputLayout userEntry_TIL_fullname, userEntry_TIL_age, userEntry_TIL_address;
    private TextInputEditText userEntry_TIET_fullname, userEntry_TIET_age, userEntry_TIET_address;
    private Button userEntry_button_save;

    private String action;
//    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_entry);

        initialize();
        setValue();
        setListener();
    }

    private void setListener() {
        userEntry_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        userEntry_button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = userEntry_TIL_fullname.getEditText().getText().toString().trim();
                int age =  Integer.parseInt(userEntry_TIL_age.getEditText().getText().toString().trim());
                String address = userEntry_TIL_address.getEditText().getText().toString().trim();
                User user = new User(fullname, age, address);


                if (action.equalsIgnoreCase("add")) {
                    Intent intent = new Intent();
                    intent.putExtra("user", user);

                    setResult(1, intent);

                    Toast.makeText(getBaseContext(), "User saved", Toast.LENGTH_SHORT).show();

                    finish();
                } else if (action.equalsIgnoreCase("edit")) {
                    Intent intent = new Intent();
//                    intent.putExtra("position", position);
                    intent.putExtra("user", user);

                    setResult(2, intent);

                    Toast.makeText(getBaseContext(), "User updated", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }

    private void setValue() {
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
//        position = intent.getIntExtra("position", -1);
        User user = intent.getParcelableExtra("user");

        if (action.equalsIgnoreCase("add")) {
            userEntry_toolbar.setTitle("Add User");
            userEntry_button_save.setText("Save Data");
        } else if (action.equalsIgnoreCase("edit")) {
            userEntry_TIL_fullname.getEditText().setText(user.getFullname());
            userEntry_TIL_age.getEditText().setText(String.valueOf(user.getAge()));
            userEntry_TIL_address.getEditText().setText(user.getAddress());
            userEntry_toolbar.setTitle("Edit User");
            userEntry_button_save.setText("Update Data");
        }
    }

    private void initialize() {
        userEntry_toolbar = findViewById(R.id.userEntry_toolbar);
        userEntry_TIL_fullname = findViewById(R.id.userEntry_TIL_fullname);
        userEntry_TIL_age = findViewById(R.id.userEntry_TIL_age);
        userEntry_TIL_address = findViewById(R.id.userEntry_TIL_address);
        userEntry_TIET_fullname = findViewById(R.id.userEntry_TIET_fullname);
        userEntry_TIET_age = findViewById(R.id.userEntry_TIET_age);
        userEntry_TIET_address = findViewById(R.id.userEntry_TIET_address);
        userEntry_button_save = findViewById(R.id.userEntry_button_save);
    }
}