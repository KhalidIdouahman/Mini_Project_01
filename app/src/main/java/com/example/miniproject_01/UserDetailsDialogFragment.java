package com.example.miniproject_01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.io.InputStream;

public class UserDetailsDialogFragment extends DialogFragment {
    UserModel user;

    public UserDetailsDialogFragment(UserModel user) {
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_user_details , container , false);
        TextView userFnameDetails = view.findViewById(R.id.user_ft_name_details);
        TextView userLnameDetails = view.findViewById(R.id.user_lt_name_details);
        TextView userCityDetails = view.findViewById(R.id.user_city_details);
        ImageView userImageDetails = view.findViewById(R.id.user_image_details);

        userFnameDetails.setText(user.getFirstName());
        userLnameDetails.setText(user.getLastName());
        userCityDetails.setText(user.getCity());

        try {
            InputStream inputStream = requireContext().getAssets().open(user.getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            userImageDetails.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user.getGender().equals("male")) {
            view.setBackgroundColor(Color.parseColor("#ADD8E6"));
        } else  {
            view.setBackgroundColor(Color.parseColor("#FFB6C1"));
        }
        return view;
    }
}
