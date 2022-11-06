package com.example.lab4_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lab4_iot.entity.Hito;
import com.example.lab4_iot.entity.Jugador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    ArrayList<String> listaNombres;
    ArrayList<String> listaApellidos;
    ArrayList<String> listaEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseDatabase = FirebaseDatabase.getInstance();

        String[] equipos = {"Alianza Lima","Universitario de Deportes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,equipos);
        Spinner spinnerEquipos = findViewById(R.id.spinnerEquipo);
        spinnerEquipos.setAdapter(adapter);

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Alianza Lima");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren() ){
                    Jugador jugador = children.getValue(Jugador.class);
                    listaNombres.add(jugador.getNombre());
                    listaApellidos.add(jugador.getApellido());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("Universitario de Deportes");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren() ){
                    Jugador jugador = children.getValue(Jugador.class);
                    listaNombres.add(jugador.getNombre());
                    listaApellidos.add(jugador.getApellido());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        ArrayList<String> jugadores = listaNombres;
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,jugadores);
        Spinner spinnerNombre = findViewById(R.id.spinnerNombre);
        spinnerNombre.setAdapter(adapter1);

        ArrayList<String> apellidos = listaApellidos;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,apellidos);
        Spinner spinnerApellido = findViewById(R.id.spinnerApellido);
        spinnerApellido.setAdapter(adapter2);


        Button button = findViewById(R.id.btnAdmin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


}