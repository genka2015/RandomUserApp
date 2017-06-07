package com.example.android.randomuserapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private static final String BASE_URL = "https://randomuser.me/api";
    private static final String RETROFIT_URL = "https://randomuser.me/";

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ADDRESS = "USER_ADDRESS";
    private static final String USER_EMAIL = "USER_EMAIL";

    private Button user_bt;
    private Button listView_bt;
    private Button recyclerView_bt;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString(USER_NAME);
            String address = msg.getData().getString(USER_ADDRESS);
            String email = msg.getData().getString(USER_EMAIL);
            postResult(name, address, email);
        }
    };

    TextView responseName;
    TextView responseAddress;
    TextView responseEmail;
    private ArrayList <User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bindings ( dependency injection, data binding, etc) set up what you need
        responseName = (TextView) findViewById(R.id.result_name);
        responseAddress = (TextView) findViewById(R.id.result_address);
        responseEmail = (TextView) findViewById(R.id.result_email);
        user_bt = (Button) findViewById(R.id.get_user_bt);
        listView_bt = (Button) findViewById(R.id.list_view_bt);
        recyclerView_bt = (Button) findViewById(R.id.recycler_view_bt);
        user_bt.setOnClickListener(this);
        listView_bt.setOnClickListener(this);
        recyclerView_bt.setOnClickListener(this);
        userList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // doNativeNetworkCall();
        //doOkHttpNetworkCall();
        //doRetrofitNetworkCall();
    }

    private void postResult(String n, String a, String e) {
        String resultName = String.format(getString(R.string.lbl_result_name),n);
        String resultAddress = String.format(getString(R.string.lbl_result_address),a);
        String resultEmail = String.format(getString(R.string.lbl_result_email),e);

        responseName.setText(resultName);
        responseAddress.setText(resultAddress);
        responseEmail.setText(resultEmail);
    }

    private void doRetrofitNetworkCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RETROFIT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        retrofit2.Call<RandomAPI> call = service.getRandomUser();
        call.enqueue(new retrofit2.Callback<RandomAPI>() {
            @Override
            public void onResponse(retrofit2.Call<RandomAPI> call, retrofit2.Response<RandomAPI> response) {
                if(response.isSuccessful()){
                    Message msg = handler.obtainMessage();
                    Bundle data = new Bundle();
                    String currentName = "";
                    String currentAddress = "";
                    String currentEmail = "";

                    RandomAPI randomAPI = response.body();
                    for(Result result:randomAPI.getResults()){
                        Log.d(TAG, "onResponse: Name is " + result.getName());

                        currentName = result.getName().getTitle() + " " + result.getName().getFirst() + " " + result.getName().getLast();
                        currentAddress = result.getLocation().getStreet() + " " + result.getLocation().getCity() + " " + result.getLocation().getState() + " " + Integer.toString(result.getLocation().getPostcode());
                        currentEmail = result.getEmail();
                        data.putString(USER_NAME, currentName);
                        data.putString(USER_ADDRESS, currentAddress);
                        data.putString(USER_EMAIL, currentEmail);
                        User currentUser = new User(currentName,currentAddress,currentEmail);
                        userList.add(currentUser);

                    }
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
                else{}
            }

            @Override
            public void onFailure(retrofit2.Call<RandomAPI> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_user_bt:
                doRetrofitNetworkCall();
                break;
            case R.id.list_view_bt:
                // go to List View
                Intent listViewIntent = new Intent(MainActivity.this,ListViewActivity.class);

                listViewIntent.putParcelableArrayListExtra("data",userList);
                startActivity(listViewIntent);
                break;
            case R.id.recycler_view_bt:
                // go to Recycler View
                Intent recyclerViewIntent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                recyclerViewIntent.putParcelableArrayListExtra("data",userList);
                startActivity(recyclerViewIntent);
                break;
        }


    }
}

