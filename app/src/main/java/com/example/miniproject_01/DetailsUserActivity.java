package com.example.miniproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.miniproject_01.databinding.ActivityDetailsUserBinding;

public class DetailsUserActivity extends AppCompatActivity {

    ActivityDetailsUserBinding detailsUserBindingViews;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsUserBindingViews = ActivityDetailsUserBinding.inflate(getLayoutInflater());
        view = detailsUserBindingViews.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();

        detailsUserBindingViews.userFirstNameTv.setText(bundle.getString("user_ft_name"));
        detailsUserBindingViews.userLastNameTv.setText(bundle.getString("user_lt_name"));
        detailsUserBindingViews.userCityNameTv.setText(bundle.getString("user_city"));
        if (bundle.getString("user_gender").equals("male")) {
            detailsUserBindingViews.userGenderIv.setImageResource(R.drawable.ic_male);
        } else {
            detailsUserBindingViews.userGenderIv.setImageResource(R.drawable.ic_female);
        }
    }
}