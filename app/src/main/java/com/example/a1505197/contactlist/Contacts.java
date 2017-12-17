package com.example.a1505197.contactlist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 1505197 on 9/13/2017.
 */

public class Contacts implements Parcelable {
    private String name;
    private String phonenumber;
    private String device;
    private String email;
    private String profileImage;

    public Contacts(String name, String phonenumber, String device, String email, String profileImage) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.device = device;
        this.email = email;
        this.profileImage = profileImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getDevice() {
        return device;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
