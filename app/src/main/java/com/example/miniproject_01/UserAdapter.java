package com.example.miniproject_01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    ArrayList<UserModel> usersList ;
    Context context;
//    LayoutInflater inflater;

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
        TextView userId = convertView.findViewById(R.id.user_id_tv);

        userFullName.setText(user.fullName());
        userCity.setText(user.getCity());
        userId.setText("#" + (position + 1));

        if (user.getGender().equals("male")) {
            convertView.setBackgroundColor(Color.parseColor("#5ED4E3"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#EF63CC"));
        }
        return convertView;
    }
}
