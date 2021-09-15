package com.ss.UserListApplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int age;
    private String fullname, address;

    public User() {
        this.fullname = "";
        this.age = -1;
        this.address = "";
    }

    public User(String fullname, int age, String address) {
        this.fullname = fullname;
        this.age = age;
        this.address = address;
    }

    protected User(Parcel in) {
        age = in.readInt();
        fullname = in.readString();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(fullname);
        dest.writeString(address);
    }
}
