package com.example.miniproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingViews = ActivityMainBinding.inflate(getLayoutInflater());
        root = bindingViews.getRoot();
        setContentView(root);

        bindingViews.loadBtn.setOnClickListener(this);
        bindingViews.quitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == bindingViews.loadBtn.getId()) {
            String data = getDataFromJson("users.json");
            ArrayList<String> usersNames = getUsersFullNames(data);
            ArrayAdapter<String> dataOfListView = new ArrayAdapter<>(this ,
                    android.R.layout.simple_list_item_1 , usersNames);
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

    public ArrayList<String> getUsersFullNames(String jsonContent) {
        ArrayList<String> fullNamesList = new ArrayList<>();
        try {
            JSONObject mainObj = new JSONObject(jsonContent);
            JSONArray users = mainObj.getJSONArray("users");
            String fullName = "";
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                JSONObject name = user.getJSONObject("name");
                fullName = String.format("%s %s " , name.get("first") , name.get("last"));
                fullNamesList.add(fullName);
            }
//            Toast.makeText(this, fullName, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fullNamesList;
    }
}