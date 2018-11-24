package com.example.rahul.cpermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=23){
            checkPermissions();
        }
        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
    }
    // Show Android Version and other Details
    public void showAndroidDetails(){
        TextView tv = (TextView) findViewById(R.id.introText);
        tv.setText(Build.VERSION.SDK_INT+" -- "+Build.VERSION.BASE_OS
                +" -- "+Build.VERSION.RELEASE);
    }

    // Checks if external storage is available for read and write
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Initiate request for permissions.
    private void checkPermissions() {

        if (!isExternalStorageWritable()) {
            TextView tv = findViewById(R.id.introText);
            tv.setText("This app only works on devices with usable external storage");

        }else{
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            /*if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_WRITE);
            }*/
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_WRITE);

            } else {
                    showAndroidDetails();
            }
        }
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
                    ActivityCompat.finishAffinity(MainActivity.this);
                }
                break;
        }
    }


    // Using  custom separate methods
   /* public void closeNow(){
        if(Build.VERSION.SDK_INT>16){
            // Checking OS version is above JELLY BEAN
            finishAffinity();
        }else{
            finish();
        }
    }*/
}
