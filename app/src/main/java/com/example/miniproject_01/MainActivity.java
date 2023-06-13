package com.example.miniproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
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
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding bindingViews;
    View root;
    LoadingData asyncLoadingData;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingViews = ActivityMainBinding.inflate(getLayoutInflater());
        root = bindingViews.getRoot();
        setContentView(root);

        asyncLoadingData = new LoadingData();

        bindingViews.loadBtn.setOnClickListener(this);
        bindingViews.userSwipeTv.setOnTouchListener(new MyOnSwipeListener(this) {
            @Override
            public void swipeLeft() {
                finish();
                asyncLoadingData.cancel(true);
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
            ArrayList<UserModel> usersInfos = null;
            try {
                usersInfos = asyncLoadingData.execute("users.json").get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            UserAdapter dataOfListView = new UserAdapter(this, usersInfos);
            bindingViews.usersListView.setAdapter(dataOfListView);
        }
    }

    public class LoadingData extends AsyncTask<String, Void, ArrayList<UserModel>> {
        ProgressBar bar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bindingViews.loadBtn.setEnabled(false);

            bar = new ProgressBar(MainActivity.this);
            Toast.makeText(MainActivity.this, "onPreExecute", Toast.LENGTH_SHORT).show();
            bar.setActivated(true);
            bar.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
//            Toast.makeText(MainActivity.this, "doInBackground", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<UserModel> doInBackground(String... strings) {
            ArrayList<UserModel> usersList = new ArrayList<>();
            String dataFromJsonFile = getDataFromJson(strings[0]);
            try {
                JSONObject mainObj = new JSONObject(dataFromJsonFile);
                JSONArray users = mainObj.getJSONArray("users");
                for (int i = 0; i < users.length(); i++) {
                    if (!isCancelled()) {
                        JSONObject user = users.getJSONObject(i);
                        Thread.sleep(150);
                        JSONObject name = user.getJSONObject("name");
                        usersList.add(new UserModel(name.getString("first"), name.getString("last"),
                                user.getString("gender"), user.getString("city")));
                    } else {
                        return null;
                    }
                }
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
            return usersList;
        }

        @Override
        protected void onPostExecute(ArrayList<UserModel> userModels) {
            super.onPostExecute(userModels);
//            Toast.makeText(MainActivity.this, "onPostExecute", Toast.LENGTH_SHORT).show();
            bindingViews.loadBtn.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onCancelled(ArrayList<UserModel> userModels) {
            super.onCancelled(userModels);
//            Toast.makeText(MainActivity.this, "onCancelled", Toast.LENGTH_SHORT).show();
        }

        private String getDataFromJson(String fileName) {
            StringBuilder contentOfFile = new StringBuilder();
            ;
            try {
                InputStream inputStream = getAssets().open(fileName);
                int code;
                while ((code = inputStream.read()) != -1) {
                    contentOfFile.append((char) code);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return contentOfFile.toString();
        }

//        private ArrayList<UserModel> getUsers(String jsonContent) {
//            ArrayList<UserModel> usersList = new ArrayList<>();
//            try {
//                JSONObject mainObj = new JSONObject(jsonContent);
//                JSONArray users = mainObj.getJSONArray("users");
//                for (int i = 0; i < users.length(); i++) {
//                    JSONObject user = users.getJSONObject(i);
//                    Thread.sleep(200);
//                    JSONObject name = user.getJSONObject("name");
//                    usersList.add(new UserModel(name.getString("first"), name.getString("last"), user.getString("gender"),
//                            user.getString("city")));
//                }
////            Toast.makeText(this, fullName, Toast.LENGTH_SHORT).show();
//            } catch (JSONException | InterruptedException e) {
//                e.printStackTrace();
//            }
//            return usersList;
//        }
    }
}