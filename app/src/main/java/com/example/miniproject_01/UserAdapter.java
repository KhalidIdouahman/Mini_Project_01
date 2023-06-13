package com.example.miniproject_01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
    private final int DURATION_OF_DOUBLE_CLICK = 250;
    public UserAdapter(Context context , ArrayList<UserModel> usersList) {
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

        View item = convertView;
        convertView.setOnTouchListener(new MyOnSwipeListener(context) {

            @Override
            public void swipeLeft() {
                item.setBackgroundColor(Color.parseColor("#FF9F9F"));
               AlertDialog.Builder alert = new AlertDialog.Builder(context);
               alert.setTitle("Attention").setMessage("Do you want to remove this user ?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               usersList.remove(position);
                               notifyDataSetChanged();
                               Toast.makeText(context, "User removed !!", Toast.LENGTH_SHORT).show();
                               item.setBackgroundColor(Color.parseColor("#FFFFFF"));

                           }
                       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(context, "The process of removal this user has been canceled .", Toast.LENGTH_SHORT).show();
                               item.setBackgroundColor(Color.parseColor("#FFFFFF"));
                           }
                       }).show();

            }
        });

        return convertView;
    }
}
