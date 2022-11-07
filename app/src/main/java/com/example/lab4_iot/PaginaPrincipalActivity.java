package com.example.lab4_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class PaginaPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        //Inicializamos las variables

        ((Button) findViewById(R.id.btnLogAdmin)).setOnClickListener(view -> {
            Intent intent =  new Intent(PaginaPrincipalActivity.this,LogInActivityUser.class);
            intent.putExtra("ROL","ADMIN");
            startActivity(intent);
        });
        ((Button) findViewById(R.id.btnLogUser)).setOnClickListener(view -> {
            Intent intent =  new Intent(PaginaPrincipalActivity.this,LogInActivityUser.class);
            intent.putExtra("ROL","USUARIO");
            startActivity(intent);
        });
    }
    private final ActivityResultLauncher<Intent> signInLauncher= registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                onSignInResult(result);
            }
    );
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result){
        IdpResponse response = result.getIdpResponse();
        if(result.getResultCode() == RESULT_OK){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d("log-in","Sí se logueo");
            Log.d("log-in","Firebase User: "+user.getUid());
        }else{
            Log.d("log-in","NO LOGUEO");
        }
    }

}