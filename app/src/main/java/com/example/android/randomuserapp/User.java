package com.example.android.randomuserapp;


import android.os.Parcel;
import android.os.Parcelable;

class User implements Parcelable{

    private String name;
    private String address;
    private String email;

    public User(){

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];

        }
    };

    public User(String n, String a, String e){
        name = n;
        address = a;
        email = e;
    }

    private User(Parcel in) {
        name = in.readString();
        address = in.readString();
        email = in.readString();

    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(email);
    }
}
