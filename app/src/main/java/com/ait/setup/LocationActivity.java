package com.ait.setup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationActivity extends Activity {

    private static final int PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check whether GPS is enabled or not
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finish();
        }

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //If the app doesnt have access then you have to request for permission

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTracker();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            //Then start GPS tracking
            startTracker();

        }else{
            Toast.makeText(LocationActivity.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTracker(){
        startService(new Intent(this, MyService.class));

        Toast.makeText(this,"GPS Tracking Enabled", Toast.LENGTH_SHORT).show();

        finish();
    }
}