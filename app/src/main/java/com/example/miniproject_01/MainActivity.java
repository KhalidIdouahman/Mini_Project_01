package com.example.miniproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miniproject_01.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

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
            try {
                InputStream inputStream = getAssets().open("users.json");
                int code;
                StringBuilder contentOfFile = new StringBuilder();

                while ((code = inputStream.read()) != -1) {
                    contentOfFile.append((char)code);
                }

                JSONObject mainObj = new JSONObject(contentOfFile.toString());
                JSONArray users = mainObj.getJSONArray("users");
                String fullName = "";
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    JSONObject name = user.getJSONObject("name");
                    fullName += String.format("%s %s \n" , name.get("first") , name.get("last"));
                }
                Toast.makeText(this, fullName, Toast.LENGTH_SHORT).show();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else  {
            finish();
        }
    }
}