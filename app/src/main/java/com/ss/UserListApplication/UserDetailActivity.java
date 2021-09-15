package com.ss.UserListApplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ss.UserListApplication.R;
import com.ss.UserListApplication.model.User;

public class UserDetailActivity extends AppCompatActivity {

    private Toolbar userDetail_toolbar;
    private TextView userDetail_textView_fullname, userDetail_textView_age, userDetail_textView_address;
    private ImageButton userDetail_imageButton_edit, userDetail_imageButton_delete;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private User user;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        initialize();
        setValue();
        setCallback();
        setListener();
    }

    private void setListener() {
        userDetail_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        userDetail_imageButton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserEntryActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra("user", user);

                activityResultLauncher.launch(intent);
            }
        });
        
        userDetail_imageButton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserDetailActivity.this);
                dialog.setTitle("Delete user")
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setMessage("Are you sure you want to delete " + user.getFullname() + "?" +
                                "\n\nThis action cannot be undone!")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.putExtra("position", position);

                                setResult(3, intent);

                                Toast.makeText(getBaseContext(), "User deleted", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    private void setCallback() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Result code
                        // 2 = Edit user success
                        if (result.getResultCode() == 2) {
                            User user = result.getData().getParcelableExtra("user");

                            Intent intent = new Intent();
                            intent.putExtra("position", position);
                            intent.putExtra("user", user);

                            setResult(2, intent);

                            finish();
                        }
                    }
                });
    }

    private void setValue() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        user = intent.getParcelableExtra("user");
        userDetail_textView_fullname.setText(user.getFullname());
        userDetail_textView_age.setText(String.valueOf(user.getAge()));
        userDetail_textView_address.setText(user.getAddress());
    }

    private void initialize() {
        userDetail_toolbar = findViewById(R.id.userDetail_toolbar);
        userDetail_textView_fullname = findViewById(R.id.userDetail_textView_fullname);
        userDetail_textView_age = findViewById(R.id.userDetail_textView_age);
        userDetail_textView_address = findViewById(R.id.userDetail_textView_address);
        userDetail_imageButton_edit = findViewById(R.id.userDetail_imageButton_edit);
        userDetail_imageButton_delete = findViewById(R.id.userDetail_imageButton_delete);
    }
}