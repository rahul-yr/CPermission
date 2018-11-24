package com.example.rahul.cpermission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE = 1500;
    private String perm1 = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            new CheckPermissionsDao(perm1, MainActivity.this);
        }
        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                    showAndroidDetails();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                    // closeNow();
                    ActivityCompat.finishAffinity(this);
                }
                break;
        }
    }

    public void showAndroidDetails() {
        TextView tv = findViewById(R.id.introText);
        tv.setText(Build.VERSION.SDK_INT + " -- " + Build.VERSION.BASE_OS
                + " -- " + Build.VERSION.RELEASE);
    }

    // Using  custom separate methods
   /*
   public void closeNow(){
        if(Build.VERSION.SDK_INT>16){
            // Checking OS version is above JELLY BEAN
            finishAffinity();
        }else{
            finish();
        }
    }*/
}
