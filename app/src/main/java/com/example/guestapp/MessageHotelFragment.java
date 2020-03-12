package com.example.guestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MessageHotelFragment extends Fragment {

    private EditText mEditTextSubject;
    private EditText mEditTextMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messagehotel, container, false);
        mEditTextSubject = v.findViewById(R.id.edit_text_subject);
        mEditTextMessage = v.findViewById(R.id.edit_text_message);
        Button buttonSend = v.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        return v;
    }

    private void sendMail(){
        String recipientList = "info@hotelbeila.be";
        String[]recipients = recipientList.split(",");

        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT,message);

        sendIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(sendIntent,"Choose an email client"));
    }
}
