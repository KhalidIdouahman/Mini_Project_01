package com.example.miniproject_01;

import androidx.appcompat.app.AlertDialog;
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
            try {
                InputStream inputStream = getAssets().open("users.json");
                int code;
                StringBuilder contentOfFile = new StringBuilder();

                while ((code = inputStream.read()) != -1) {
                    contentOfFile.append((char)code);
                }

                JSONObject mainObj = new JSONObject(contentOfFile.toString());
                JSONArray users = mainObj.getJSONArray("users");

                ArrayList<JSONObject> maleUsers = new ArrayList<>();
                ArrayList<JSONObject> femaleUsers = new ArrayList<>();
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    if (user.getString("gender").equals("male")) {
                       maleUsers.add(user);
                    } else {
                        femaleUsers.add(user);
                    }
                }


                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                String userInfos = "";

                if (bindingViews.malesRadioBtn.isChecked()) {
                    for (int i = 0; i < maleUsers.size(); i++) {
                        JSONObject maleUser = maleUsers.get(i);
                        JSONObject name = maleUser.getJSONObject("name");
                        userInfos += String.format("%s %s | %s \n" , name.get("first") , name.get("last") , maleUser.getString("city"));
                    }
                    alert.setTitle("Male Users").setMessage(userInfos).show();
                } else {
                    for (int i = 0; i < femaleUsers.size(); i++) {
                        JSONObject femaleUser = femaleUsers.get(i);
                        JSONObject name = femaleUser.getJSONObject("name");
                        userInfos += String.format("%s %s | %s \n" , name.get("first") , name.get("last") , femaleUser.getString("city"));
                    }
                    alert.setTitle("Female Users").setMessage(userInfos).show();

                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else  {
            finish();
        }
    }
}