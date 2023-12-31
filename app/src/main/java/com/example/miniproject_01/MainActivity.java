package com.example.miniproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproject_01.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding bindingViews;
    View root;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingViews = ActivityMainBinding.inflate(getLayoutInflater());
        root = bindingViews.getRoot();
        setContentView(root);

        bindingViews.loadBtn.setOnClickListener(this);
        bindingViews.userSwipeTv.setOnTouchListener(new MyOnSwipeListener(this) {
            @Override
            public void swipeLeft() {
                finish();
            }
        });
        //region i try all the lines of code when the swipe didn't work
        //        bindingViews.userSwipeTv.setOnClickListener(v -> {
//            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
//        });
//        TextView swipe = findViewById(R.id.user_swipe_tv);
//
//        swipe.setOnTouchListener(new MyOnSwipeListener(this) {
//            @Override
//            public void swipeLeft() {
//                Log.e("TAG", "onFling: ");
//                Toast.makeText(MainActivity.this, "enter here", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
        //endregion
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == bindingViews.loadBtn.getId()) {
            String data = getDataFromJson("users.json");
            ArrayList<UserModel> usersInfos = getUsers(data);
            UserAdapter dataOfListView = new UserAdapter(this , usersInfos , getSupportFragmentManager());
            bindingViews.usersListView.setAdapter(dataOfListView);
        } else  {
            finish();
        }
    }

    public String getDataFromJson(String fileName) {
        StringBuilder contentOfFile = new StringBuilder();;
        try {
            InputStream inputStream = getAssets().open(fileName);
            int code;
            while ((code = inputStream.read()) != -1) {
                contentOfFile.append((char)code);
            }

        } catch (IOException e ) {
            e.printStackTrace();
        }
        return contentOfFile.toString();
    }

    public ArrayList<UserModel> getUsers(String jsonContent) {
        ArrayList<UserModel> usersList = new ArrayList<>();
        try {
            JSONObject mainObj = new JSONObject(jsonContent);
            JSONArray users = mainObj.getJSONArray("users");
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                JSONObject name = user.getJSONObject("name");
                usersList.add(new UserModel(name.getString("first") , name.getString("last") , user.getString("gender"),
                user.getString("city")));
            }
//            Toast.makeText(this, fullName, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usersList;
    }
}