package com.example.guestapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {

int[]images = {R.drawable.monday,R.drawable.tuesday,R.drawable.wednesday,R.drawable.thursday,R.drawable.friday,R.drawable.saturday,R.drawable.sunday};
RecyclerView recyclerView;
Users currentUser;
private boolean mTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }
        if(savedInstanceState != null && currentUser == null){
            currentUser = savedInstanceState.getParcelable("Current user");
            if(currentUser != null){
                String welcome = "Hello, " + currentUser.getFirstName() + "!";
                TextView welcomeTextView = v.findViewById(R.id.welcome);
                welcomeTextView.setText(welcome);
                TextView welcomeInfoTextView = v.findViewById(R.id.welcomeinfo);
                String welcomeInfo = "Name: " + currentUser.getFirstName() + " " + currentUser.getName() + "\nE-Mail: " + currentUser.getEmail() + "\nCheck-In-Code: " + getData()+"\n\nPrevious bookings:";
                welcomeInfoTextView.setText(welcomeInfo);
                hideSoftKeyboard(getActivity());
                recyclerView = v.findViewById(R.id.recyclerView);
                MyAdapter myAdapter = new MyAdapter(this.getContext(),images,currentUser.getLastBookings(), currentUser);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                onSaveInstanceState(savedInstanceState);
            }
        }

        return v;
    }

    private String getData(){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("checkinCode",MODE_PRIVATE);
        String selectedData = mSharedPreferences.getString("checkinCode","Unknown");
        return selectedData;
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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
