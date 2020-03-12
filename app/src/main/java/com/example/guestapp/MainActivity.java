package com.example.guestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    Bundle bundle;
    CheckInFragment checkInFragment = new CheckInFragment();
    MessageHotelFragment messageHotelFragment = new MessageHotelFragment();
    SettingsActivity settingsActivity = new SettingsActivity();
    CameraFragment cameraFragment = new CameraFragment();

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check-In-Ride");

        drawer = findViewById(R.id.drawer_layout);
        bundle = new Bundle();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new CheckInFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_checkin);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_checkin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        checkInFragment).commit();
                navigationView.setCheckedItem(R.id.nav_checkin);
                break;
            case R.id.nav_findhotel:
                if(isServicesOK()){
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putExtra("used bundle", bundle);
                startActivity(intent);}
                break;
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        messageHotelFragment).commit();
                navigationView.setCheckedItem(R.id.nav_message);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingsActivity).commit();
                navigationView.setCheckedItem(R.id.nav_checkin);
                break;
            case R.id.nav_camera:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,cameraFragment).commit();
                navigationView.setCheckedItem(R.id.nav_camera);

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this,"We can't make map request",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        PortraitFragment myFragment = (PortraitFragment)getSupportFragmentManager().findFragmentByTag("PORTRAITTAG");

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            try {
                recreate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            try {
                if(myFragment != null && myFragment.isVisible()){
                    Users currentUser = myFragment.getCurrentUser();
                    Bundle detailBundle = new Bundle();
                    detailBundle.putParcelable("Current user",currentUser);
                    detailBundle.putString("day descr", myFragment.getDayDescrString());
                    detailBundle.putString("day",myFragment.getDayString());
                    detailBundle.putString("month",myFragment.getMonthString());
                    detailBundle.putString("year", myFragment.getYearString());
                    LandPortraitFragment landPortraitFragment = new LandPortraitFragment();
                    landPortraitFragment.setArguments(detailBundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, landPortraitFragment).addToBackStack(null).commit();
                } else {
                    recreate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
