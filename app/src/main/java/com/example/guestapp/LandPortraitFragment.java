package com.example.guestapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LandPortraitFragment extends Fragment {
    Users currentUser;
    RecyclerView recyclerView;
    int[]images = {R.drawable.monday,R.drawable.tuesday,R.drawable.wednesday,R.drawable.thursday,R.drawable.friday,R.drawable.saturday,R.drawable.sunday};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.portrait_land, container, false);

        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }
        if(savedInstanceState != null && currentUser == null){
            currentUser = savedInstanceState.getParcelable("Current user");
            if(currentUser != null){
                recyclerView = v.findViewById(R.id.recyclerView);
                String welcome = "Hello, " + currentUser.getFirstName() + "!";
                TextView welcomeTextView = v.findViewById(R.id.welcome);
                welcomeTextView.setText(welcome);
                TextView welcomeInfoTextView = v.findViewById(R.id.welcomeinfo);
                String welcomeInfo = "Name: " + currentUser.getFirstName() + " " + currentUser.getName() + "\nE-Mail: " + currentUser.getEmail() + "\nCheck-In-Code: " + currentUser.getCheckincode()+"\n\nPrevious bookings:";
                welcomeInfoTextView.setText(welcomeInfo);
                MyAdapter myAdapter = new MyAdapter(this.getContext(),images,currentUser.getLastBookings(), currentUser);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                int currentOrientation = getResources().getConfiguration().orientation;
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle dataBundle = new Bundle();
                    String dayDescrString = getArguments().getString("day descr");
                    String dayString = getArguments().getString("day");
                    String monthString = getArguments().getString("month");
                    String yearString = getArguments().getString("year");
                    dataBundle.putString("day descr",dayDescrString);
                    dataBundle.putString("day",dayString);
                    dataBundle.putString("month",monthString);
                    dataBundle.putString("year", yearString);
                    dataBundle.putParcelable("Current user", currentUser);
                    detailFragment.setArguments(dataBundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.detail, detailFragment).addToBackStack(null).commit();
                }
            }
        }

        return v;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable("Current user", currentUser);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        if(savedInstanceState != null){
            currentUser = savedInstanceState.getParcelable("Current user");
        }
    }
}
