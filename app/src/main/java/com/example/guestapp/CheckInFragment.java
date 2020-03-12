package com.example.guestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CheckInFragment extends Fragment {

    private EditText editText;
    public Users currentUser;
    String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_checkin, container, false);

        TextView responseView = v.findViewById(R.id.succesText);
        editText = v.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text = editText.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").whereEqualTo("CheckInCode",text).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        Log.d("outputData",document.getId()+ " => " + document.getData());
                                        responseView.setVisibility(View.VISIBLE);
                                        String name = document.getString("Naam");
                                        String firstName = document.getString("Voornaam");
                                        String email = document.getString("Email");
                                        String checkInCode = document.getString("CheckInCode");
                                        List<Timestamp> previousBookings = (List<Timestamp>) document.get("Vorige boekingen");
                                        currentUser = new Users(name,firstName,email,checkInCode,previousBookings);
                                        AccountFragment accountFragment = new AccountFragment();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("Current user",currentUser);
                                        accountFragment.setArguments(bundle);
                                        fragmentTransaction.replace(R.id.fragment_container,accountFragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                        hideSoftKeyboard(getActivity());
                                        storeData(checkInCode);
                                    }
                                } else {
                                       responseView.setVisibility(View.VISIBLE);
                                       responseView.setText("Login failed. Please try again");
                                }
                            }
                        });
            }
        });

        return v;
    }

    private void storeData(String key){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("checkinCode",MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("checkinCode",key);
        mEditor.apply();
    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            return false;
        }
    };

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


}
