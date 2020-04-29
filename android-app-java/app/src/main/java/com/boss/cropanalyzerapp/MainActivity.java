package com.boss.cropanalyzerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 101;
    private static final int RESET_VIEWS = 102;
    String TAG = "MainActivity";
    Boolean userSelectedImage = false;

    Button uploadButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        clickListeners();
    }

    private void clickListeners() {
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", String.valueOf(userSelectedImage));
                if (userSelectedImage) {
                    // Make Network Request


                    // start new activity and send JSON to this activity

                    // use start activity for result eg : startActivityForResult(intent, RESET_VIEWS)


                } else {
                    Toast.makeText(MainActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", String.valueOf(userSelectedImage));
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, requesting permission
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        uploadImage();
                    }
                } else {
                    // system os is less than marshmallow
                    uploadImage();
                }
            }
        });
    }

    public void bindViews() {
        uploadButton = findViewById(R.id.buttonUpload);
        imageView = findViewById(R.id.imageView);
    }

    private void uploadImage() {
        //intent to upload image
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handling request of runtime permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadImage();
                } else {
                    //permission denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handling result of pick image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            switch (requestCode) {
                case IMAGE_PICK_CODE: {
                    Log.e(TAG, data.getData().toString());
                    imageView.setImageURI(data.getData());
                    userSelectedImage = true;
                }
                case RESET_VIEWS: {
                    // reset the imageView
                    userSelectedImage = false;
                    // imageView.setBackground(R.drawable.ic_leaf);
                }
            }

        }
    }
}
