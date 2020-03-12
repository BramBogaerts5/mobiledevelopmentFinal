package com.example.guestapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.List;

public class Users implements Parcelable {
    private String name;
    private String firstName;
    private String email;
    private String checkincode;
    private List<Timestamp> lastBookings;

    public Users(String name, String firstName, String email, String checkincode, List<Timestamp>lastBookings){
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.checkincode = checkincode;
        this.lastBookings = lastBookings;
    }

    protected Users(Parcel in) {
        name = in.readString();
        firstName = in.readString();
        email = in.readString();
        checkincode = in.readString();
        lastBookings = in.createTypedArrayList(Timestamp.CREATOR);
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCheckincode() {
        return checkincode;
    }

    public void setCheckincode(String checkincode) {
        this.checkincode = checkincode;
    }

    public List<Timestamp>getLastBookings() {
        return lastBookings;
    }

    public void setLastBookings(List<Timestamp> lastBookings) {
        this.lastBookings = lastBookings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(firstName);
        dest.writeString(email);
        dest.writeString(checkincode);
        dest.writeTypedList(lastBookings);
    }
}
