package com.example.miniproject_01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    ArrayList<UserModel> usersList ;
    Context context;
    private final int MIN_DURATION_OF_LONG_CLICK = 1000;
    private final int MAX_DURATION_OF_LONG_CLICK = 2000;
    public UserAdapter( Context context , ArrayList<UserModel> usersList) {
        this.usersList = usersList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModel user = usersList.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_user , null);

        TextView userFullName = convertView.findViewById(R.id.user_full_name_tv);
        TextView userCity = convertView.findViewById(R.id.user_city_tv);

        userFullName.setText(user.fullName());
        userCity.setText(user.getCity());

        convertView.setOnTouchListener(new View.OnTouchListener() {
            long firstClick = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            //  This is just for if I want to work in multiple action
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        Toast.makeText(context, "action up", Toast.LENGTH_SHORT).show();
//                        break;
//                    case MotionEvent.ACTION_DOWN:
//                        Toast.makeText(context, "action down", Toast.LENGTH_SHORT).show();
//                        break;
//            }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    long secondClick = System.currentTimeMillis();
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    if ((secondClick - firstClick) <= MAX_DURATION_OF_LONG_CLICK &&
                            (secondClick - firstClick) >= MIN_DURATION_OF_LONG_CLICK) {
                        alert.setTitle("Details of User " + (position + 1))
                                .setMessage(user.toString())
                                .show();

                    } else {
                        firstClick = secondClick;
                    }

                }
                return true;
            }
        });

        return convertView;
    }
}
