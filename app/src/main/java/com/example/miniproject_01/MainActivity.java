package com.example.miniproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
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
                asyncLoadingData.cancel(true);
            }
        });

//        to create and set the progress bar to wrap content width and height .
        progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

//        to define the parameters of the progress bar inside it's parent .
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

//        to create and set the relative layout to match parent width and height
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

//        adding the progress bar to the relative layout
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar, layoutParams);

//        adding the relative layout to the root view
        bindingViews.getRoot().addView(relativeLayout);
        relativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == bindingViews.loadBtn.getId()) {
//            i declare the object here because when i click again i crashes
//            that means that it can't execute for many time in the same object , we should create a new obj everytime
//            we click
            asyncLoadingData = new LoadingData();
            asyncLoadingData.execute("users.json");
        }
    }

    public class LoadingData extends AsyncTask<String, Void, ArrayList<UserModel>> {
        ProgressBar bar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bindingViews.loadBtn.setEnabled(false);

            relativeLayout.setVisibility(View.VISIBLE);
            // i do like this because when we click again the progress bar is visible and the list still contains the data
//            from the first click on the load btn
            bindingViews.usersListView.setVisibility(View.INVISIBLE);
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
            UserAdapter dataOfListView = new UserAdapter(MainActivity.this, userModels);
            bindingViews.usersListView.setAdapter(dataOfListView);
            bindingViews.loadBtn.setEnabled(true);
            relativeLayout.setVisibility(View.GONE);
            bindingViews.usersListView.setVisibility(View.VISIBLE);
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