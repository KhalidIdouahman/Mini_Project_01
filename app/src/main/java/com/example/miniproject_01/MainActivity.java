package com.example.miniproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
    loadingDataFromJson loadingDataFromJson;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingViews = ActivityMainBinding.inflate(getLayoutInflater());
        root = bindingViews.getRoot();
        setContentView(root);

        loadingDataFromJson = new loadingDataFromJson();

        bindingViews.loadBtn.setOnClickListener(this);
        bindingViews.userSwipeTv.setOnTouchListener(new MyOnSwipeListener(this) {
            @Override
            public void swipeLeft() {
                finish();
                loadingDataFromJson.cancel(true);
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
            loadingDataFromJson.execute("users.json");
        }
    }


//    public ArrayList<UserModel> getUsers(String jsonContent) {
//        ArrayList<UserModel> usersList = new ArrayList<>();
//        try {
//            JSONObject mainObj = new JSONObject(jsonContent);
//            JSONArray users = mainObj.getJSONArray("users");
//            for (int i = 0; i < users.length(); i++) {
//                JSONObject user = users.getJSONObject(i);
//                JSONObject name = user.getJSONObject("name");
//                usersList.add(new UserModel(name.getString("first") , name.getString("last") , user.getString("gender"),
//                user.getString("city"), user.getString("image")));
//            }
////            Toast.makeText(this, fullName, Toast.LENGTH_SHORT).show();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return usersList;
//    }

    public class loadingDataFromJson extends AsyncTask<String , Void , ArrayList<UserModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<UserModel> doInBackground(String... strings) {
            String data = getDataFromJson(strings[0]);
            ArrayList<UserModel> usersList = new ArrayList<>();
            try {
                JSONObject mainObj = new JSONObject(data);
                JSONArray users = mainObj.getJSONArray("users");
                for (int i = 0; i < users.length(); i++) {
                    if (!isCancelled()) {
                        JSONObject user = users.getJSONObject(i);
//                        Thread.sleep(150);
                        JSONObject name = user.getJSONObject("name");
                        usersList.add(new UserModel(name.getString("first") , name.getString("last") , user.getString("gender"),
                                user.getString("city"), user.getString("image")));
                    } else {
                        return null;
                    }
                }
            } catch (JSONException  e) {
                e.printStackTrace();
            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return usersList;
        }
        @Override
        protected void onPostExecute(ArrayList<UserModel> userModels) {
            super.onPostExecute(userModels);
            UserAdapter dataOfListView = new UserAdapter(MainActivity.this , userModels, getSupportFragmentManager());
            bindingViews.usersListView.setAdapter(dataOfListView);
        }
        @Override
        protected void onCancelled(ArrayList<UserModel> userModels) {
            super.onCancelled(userModels);
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
    }
}