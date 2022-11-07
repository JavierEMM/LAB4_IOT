package com.example.lab4_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivityUser extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_user);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth =  FirebaseAuth.getInstance();
        //El idioma
        firebaseAuth.setLanguageCode("es-419");
        //Validamos si está logueado
        if(firebaseAuth.getCurrentUser() != null){
            firebaseAuth.getCurrentUser().reload().addOnCompleteListener(task -> {
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    Intent intent =  getIntent();
                    String rol = intent.getStringExtra("ROL");
                    if(rol!=null){
                        if(rol.equals("ADMIN")){
                            Intent intent1 = new Intent(LogInActivityUser.this,AdminActivity.class);
                            startActivity(intent1);
                        }else{
                            Intent intent1 = new Intent(LogInActivityUser.this,Activity_UserOptions.class);
                            startActivity(intent1);
                        }
                    }else{
                        Toast.makeText(LogInActivityUser.this, "Error al recibir rol", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else{
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task2 -> {
                        Toast.makeText(LogInActivityUser.this, "Mensaje de Verificacion enviado", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }else{
            ((Button) findViewById(R.id.btnLogIN)).setOnClickListener(view -> {
                EditText email = findViewById(R.id.editEmail);
                EditText password = findViewById(R.id.editPassword);
                if(password.toString().trim().isEmpty()){
                    password.setError("No dejar vacio");
                    password.requestFocus();
                }
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("No dejar vacio");
                    email.requestFocus();
                }
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogInActivityUser.this,"Logueado Correctamente",Toast.LENGTH_SHORT).show();
                                firebaseAuth.getCurrentUser().reload().addOnCompleteListener(task2 -> {
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        Intent intent =  getIntent();
                                        String rol = intent.getStringExtra("ROL");
                                        if(rol!=null){
                                            if(rol.equals("ADMIN")){
                                                Intent intent1 = new Intent(LogInActivityUser.this,AdminActivity.class);
                                                startActivity(intent1);
                                            }else{
                                                Intent intent1 = new Intent(LogInActivityUser.this,Activity_UserOptions.class);
                                                startActivity(intent1);
                                            }
                                            finish();
                                        }else{
                                            Toast.makeText(LogInActivityUser.this, "Error al recibir rol", Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }else{
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task3 -> {
                                            Toast.makeText(LogInActivityUser.this, "Mensaje de Verificacion enviado", Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                });
                        }else{
                            Toast.makeText(LogInActivityUser.this,"Error al loguear verifique",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
            ((Button) findViewById(R.id.btnRegister)).setOnClickListener(view2 -> {
                EditText email = findViewById(R.id.editEmail);
                EditText password = findViewById(R.id.editPassword);
                if(password.toString().trim().isEmpty()){
                    password.setError("No dejar vacio");
                    password.requestFocus();
                }
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("No dejar vacio");
                    email.requestFocus();
                }
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).setValue(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LogInActivityUser.this,"Registrado Correctamente",Toast.LENGTH_SHORT).show();
                                        firebaseAuth.getCurrentUser().reload().addOnCompleteListener(task2 -> {
                                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                                Intent intent =  getIntent();
                                                String rol = intent.getStringExtra("ROL");
                                                if(rol!=null){
                                                    if(rol.equals("ADMIN")){
                                                        Intent intent1 = new Intent(LogInActivityUser.this,AdminActivity.class);
                                                        startActivity(intent1);
                                                    }else{
                                                        Intent intent1 = new Intent(LogInActivityUser.this,Activity_UserOptions.class);
                                                        startActivity(intent1);
                                                    }
                                                    finish();
                                                }else{
                                                    Toast.makeText(LogInActivityUser.this, "Error al recibir rol", Toast.LENGTH_SHORT).show();
                                                }
                                                finish();
                                            }else{
                                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task3 -> {
                                                    Toast.makeText(LogInActivityUser.this, "Mensaje de Verificacion enviado", Toast.LENGTH_SHORT).show();
                                                });
                                            }
                                        });
                                    }else{
                                        Toast.makeText(LogInActivityUser.this,"No se ha registrado",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(LogInActivityUser.this,"Error al registrar",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
        }
    }
}