package com.wheels.dreamservice;

import static android.provider.Settings.ACTION_DREAM_SETTINGS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btnStart);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivityForResult(new Intent(ACTION_DREAM_SETTINGS), 0);
    }
});


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }
}