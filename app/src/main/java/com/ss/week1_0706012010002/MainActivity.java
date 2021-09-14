package com.ss.week1_0706012010002;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ss.week1_0706012010002.adapter.UserRVAdapter;
import com.ss.week1_0706012010002.model.OnCardClickListener;
import com.ss.week1_0706012010002.model.User;
import com.ss.week1_0706012010002.model.UserDetailActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnCardClickListener {

    private TextView main_textView_placeholder;
    private RecyclerView main_recyclerView_userList;
    private FloatingActionButton main_FAB_addUser;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private UserRVAdapter adapter;

    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setRecyclerView();
        setCallback();
        setListener();
    }

    @Override
    public void onClick(int position) {
        User user = userList.get(position);

        Intent intent = new Intent(getBaseContext(), UserDetailActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("position", position);
        activityResultLauncher.launch(intent);
    }

    private void setListener() {
        main_FAB_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserEntryActivity.class);
                intent.putExtra("action", "add");
                activityResultLauncher.launch(intent);
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
                        // 1 = New user success
                        // 2 = Edit user success
                        // 0 = Delete user success
                        if (result.getResultCode() == 1) {
                            User newUser = result.getData().getParcelableExtra("user");
                            userList.add(newUser);

                            adapter.notifyDataSetChanged();

                            if (!userList.isEmpty()) {
                                main_textView_placeholder.setVisibility(View.GONE);
                            } else {
                                main_textView_placeholder.setVisibility(View.VISIBLE);
                            }
                        } else if (result.getResultCode() == 2) {
                            int position = result.getData().getIntExtra("position", -1);

                            User user = result.getData().getParcelableExtra("user");

                            userList.set(position, user);

                            adapter.notifyDataSetChanged();
                        } else if (result.getResultCode() == 3) {
                            int position = result.getData().getIntExtra("position", -1);

                            userList.remove(position);

                            adapter.notifyDataSetChanged();

                            if (!userList.isEmpty()) {
                                main_textView_placeholder.setVisibility(View.GONE);
                            } else {
                                main_textView_placeholder.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext());
        main_recyclerView_userList.setLayoutManager(manager);
        main_recyclerView_userList.setAdapter(adapter);
    }

    private void initialize() {
        main_textView_placeholder = findViewById(R.id.main_textView_placeholder);
        main_recyclerView_userList = findViewById(R.id.main_recyclerView_userList);
        main_FAB_addUser = findViewById(R.id.main_FAB_addUser);

        userList = new ArrayList<User>();
        adapter = new UserRVAdapter(userList, this);
    }
}