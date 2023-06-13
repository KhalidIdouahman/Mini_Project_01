package com.example.miniproject_01;

import androidx.annotation.NonNull;

public class UserModel {
    private String firstName;
    private String lastName;
    private String gender;
    private String city;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }


    public UserModel(String firstName, String lastName, String gender, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.city = city;
    }

    public String fullName() {
        return String.format("%s %s " , this.firstName , this.lastName.toUpperCase());
    }

    @NonNull
    @Override
    public String toString() {
       return String.format("Hello , I'm %s , I'm a %s .\nI live in %s . ", this.fullName() ,
               this.gender.equals("male") ? "👨" : "👩" , this.city );
    }
}
