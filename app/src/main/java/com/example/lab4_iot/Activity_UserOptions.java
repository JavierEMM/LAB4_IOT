package com.example.lab4_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity_UserOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);
    }

    public void goAlineacion(View view){
        Intent intent = new Intent(this, Activity_UserAlineacion.class);
        startActivity(intent);
    }

    public void goHitos(View view){
        Intent intent = new Intent(this, Activity_UserHitos.class);
        startActivity(intent);
    }

}