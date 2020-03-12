package com.example.guestapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Timestamp>data;
    int[] images;
    Context context;
    Users currentUser;
    SimpleDateFormat sdf;
    String searchDate = "Mon";
    View view;


    public MyAdapter(Context context, int[] img, List<Timestamp>vorigeBoekingen, Users currentUser){
        this.context = context;
        data = vorigeBoekingen;
        images = img;
        this.currentUser = currentUser;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.myrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Long seconds = currentUser.getLastBookings().get(position).getSeconds();
        Date date = new Date(seconds*1000L);
        sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
        SimpleDateFormat onlyDayDate = new SimpleDateFormat("EEE");
        searchDate = onlyDayDate.format(date);
        switch (searchDate){
            case "Mon":
                holder.myImage.setImageResource(R.drawable.monday);
                break;
            case "Tue":
                holder.myImage.setImageResource(R.drawable.tuesday);
                break;
            case "Wed":
                holder.myImage.setImageResource(R.drawable.wednesday);
                break;
            case "Thu":
                holder.myImage.setImageResource(R.drawable.thursday);
                break;
            case "Fri":
                holder.myImage.setImageResource(R.drawable.friday);
                break;
            case "Sat":
                holder.myImage.setImageResource(R.drawable.saturday);
                break;
            case "Sun":
                holder.myImage.setImageResource(R.drawable.sunday);
                break;
            default:
                holder.myImage.setImageResource(R.drawable.monday);
                break;
        }
        holder.myText.setText(sdf.format(date));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat intentDayDescr = new SimpleDateFormat("EEEE");
                SimpleDateFormat intentMonth = new SimpleDateFormat("M");
                SimpleDateFormat intentDay = new SimpleDateFormat("d");
                SimpleDateFormat intentYear = new SimpleDateFormat("y");
                String sendDayDescr = intentDayDescr.format(date);
                String sendDay = intentDay.format(date);
                String sendMonth = intentMonth.format(date);
                String sendYear = intentYear.format(date);

                if (getScreenOrientation(context) == "SCREEN_ORIENTATION_LANDSCAPE") {
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("Current user", currentUser);
                    bundle.putString("day descr", sendDayDescr);
                    bundle.putString("day",sendDay);
                    bundle.putString("month",sendMonth);
                    bundle.putString("year", sendYear);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailFragment myFragment = new DetailFragment();
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.detail, myFragment).addToBackStack(null).commit();
                } else {
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("Current user", currentUser);
                    bundle.putString("day descr", sendDayDescr);
                    bundle.putString("day",sendDay);
                    bundle.putString("month",sendMonth);
                    bundle.putString("year", sendYear);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    PortraitFragment myFragment = new PortraitFragment();
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment,"PORTRAITTAG").addToBackStack(null).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentUser.getLastBookings().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText = itemView.findViewById(R.id.description);
            myImage = itemView.findViewById(R.id.dayImage);
        }
    }

    public String getScreenOrientation(Context context){
        final int screenOrientation = ((WindowManager)  context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        switch (screenOrientation) {
            case Surface.ROTATION_0:
                return "SCREEN_ORIENTATION_PORTRAIT";
            case Surface.ROTATION_90:
                return "SCREEN_ORIENTATION_LANDSCAPE";
            case Surface.ROTATION_180:
                return "SCREEN_ORIENTATION_PORTRAIT";
            default:
                return "SCREEN_ORIENTATION_LANDSCAPE";
        }
    }


}
