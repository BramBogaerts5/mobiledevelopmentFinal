package com.example.guestapp;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceFragmentCompat;

import static android.content.Context.MODE_PRIVATE;

public class SettingsActivity extends Fragment {
    EditText reminderInput;
    TextView reminderOutput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_activity, container, false);
        reminderInput = v.findViewById(R.id.reminderInput);
        reminderOutput = v.findViewById(R.id.reminderOutput);
        Button buttonReminder = v.findViewById(R.id.buttonReminder);
        buttonReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeText(reminderInput.getText().toString());
                reminderOutput.setText(reminderInput.getText().toString());
            }
        });

        reminderOutput.setText(getText());
        return v;
    }

    private void storeText(String text){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("reminderText",MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("reminderText",text);
        mEditor.apply();
    }

    private String getText(){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("reminderText",MODE_PRIVATE);
        String selectedData = mSharedPreferences.getString("reminderText","This is now empty");
        return selectedData;
    }
}