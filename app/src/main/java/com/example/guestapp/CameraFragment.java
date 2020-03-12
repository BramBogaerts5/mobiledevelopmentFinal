package com.example.guestapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class CameraFragment extends Fragment {

    ImageView cameraView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_fragment, container, false);

        Button btnCamera = (Button)view.findViewById(R.id.btnCamera);
        cameraView = (ImageView)view.findViewById(R.id.PictureView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        cameraView.setImageBitmap(bitmap);
        storeImage(bitmap);
    }

    private void storeImage(Bitmap bitmap){
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("imagepath","/sdcard/imh.jpeg");
        edit.commit();
    }
}
