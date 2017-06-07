package com.example.android.randomuserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ListViewActivity extends AppCompatActivity {

    private static final String TAG = "ListView";
    ListView userListView;
    ArrayList<User> users;
    UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        users = new ArrayList<>();
        users = getIntent().getParcelableArrayListExtra("data");
        userListView = (ListView) findViewById(R.id.list_users);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new UserAdapter(users, this);
        userListView.setAdapter(mAdapter);
    }
}