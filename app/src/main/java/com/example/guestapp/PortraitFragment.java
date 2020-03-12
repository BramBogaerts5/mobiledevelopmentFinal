package com.example.guestapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PortraitFragment extends Fragment {
    private Users currentUser;
    private String dayDescrString;
    private String dayString;
    private String monthString;
    private String yearString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);

        Bundle bundle = getArguments();
        TextView lastName = view.findViewById(R.id.textViewLastName);
        TextView firstName = view.findViewById(R.id.textViewFirstName);
        TextView dayDescr = view.findViewById(R.id.textViewDate);
        TextView day = view.findViewById(R.id.textViewDay);
        TextView month = view.findViewById(R.id.textViewMonth);
        TextView year = view.findViewById(R.id.textViewYear);

        if(bundle != null){
            currentUser = getArguments().getParcelable("Current user");
            dayDescrString = getArguments().getString("day descr");
            dayString = getArguments().getString("day");
            monthString = getArguments().getString("month");
            yearString = getArguments().getString("year");
            lastName.setText(currentUser.getName());
            firstName.setText(currentUser.getFirstName());
            dayDescr.setText(dayDescrString);
            day.setText(dayString);
            month.setText(monthString);
            year.setText(yearString);
        } else {
            lastName.setText("Please select a date");
        }
        //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public String getDayDescrString() {
        return dayDescrString;
    }

    public String getDayString() {
        return dayString;
    }

    public String getMonthString() {
        return monthString;
    }

    public String getYearString() {
        return yearString;
    }
}
