package com.example.lab4_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lab4_iot.entity.Hito;
import com.example.lab4_iot.entity.Jugador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AdminActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    ArrayList<String> listaNombres = new ArrayList<>();
    ArrayList<String> listaApellidos = new ArrayList<>();
    ArrayList<Jugador> listaJugadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseDatabase = FirebaseDatabase.getInstance();

        String[] equipos = {"Alianza Lima", "Universitario de Deportes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, equipos);
        Spinner spinnerEquipos = findViewById(R.id.spinnerEquipo);
        spinnerEquipos.setSelection(0);
        spinnerEquipos.setAdapter(adapter);


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Jugadores");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().iterator();
                for(DataSnapshot children : snapshot.getChildren() ){
                    Jugador jugador = children.getValue(Jugador.class);
                    listaJugadores.add(jugador);
                }
                listaNombres = new ArrayList<>();
                listaApellidos = new ArrayList<>();
                for(Jugador jugador : listaJugadores){
                    if(jugador.getEquipo().equals("Alianza Lima")){
                        listaNombres.add(jugador.getNombre());
                        listaApellidos.add(jugador.getApellido());
                    }
                }
                ArrayList<String> jugadores = listaNombres;
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_spinner_dropdown_item,jugadores);
                Spinner spinnerNombre = findViewById(R.id.spinnerNombre);
                spinnerNombre.setAdapter(adapter1);
                spinnerNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_spinner_dropdown_item, Collections.singletonList(listaApellidos.get(i)));
                        Spinner spinnerApellido = findViewById(R.id.spinnerApellido);
                        spinnerApellido.setEnabled(false);
                        spinnerApellido.setAdapter(adapter2);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        spinnerEquipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listaNombres = new ArrayList<>();
                listaApellidos = new ArrayList<>();
                String equipo = spinnerEquipos.getSelectedItem().toString();
                for(Jugador jugador : listaJugadores){
                    if(jugador.getEquipo().equals(equipo)){
                        listaNombres.add(jugador.getNombre());
                        listaApellidos.add(jugador.getApellido());
                    }
                }
                ArrayList<String> jugadores = listaNombres;
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_spinner_dropdown_item,jugadores);
                Spinner spinnerNombre = findViewById(R.id.spinnerNombre);
                spinnerNombre.setAdapter(adapter1);
                spinnerNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_spinner_dropdown_item, Collections.singletonList(listaApellidos.get(i)));
                        Spinner spinnerApellido = findViewById(R.id.spinnerApellido);
                        spinnerApellido.setEnabled(false);
                        spinnerApellido.setAdapter(adapter2);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button button = findViewById(R.id.btnAdmin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spinner spinnerEquipo = findViewById(R.id.spinnerEquipo);
                String equipoStr = spinnerEquipo.getSelectedItem().toString();

                DatabaseReference ref = firebaseDatabase.getReference().child("Hitos");

                Spinner spinnerNombre = findViewById(R.id.spinnerNombre);
                String nombreStr = spinnerNombre.getSelectedItem().toString();

                Spinner spinnerApellido = findViewById(R.id.spinnerApellido);
                String apellidoStr = spinnerApellido.getSelectedItem().toString();

                EditText editTextHito = findViewById(R.id.editTextHito);
                String hitoStr = editTextHito.getText().toString();

                if(hitoStr.isEmpty()){
                    Toast.makeText(AdminActivity.this, "Hito no puede estar vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                Hito hito = new Hito();
                hito.setNombre(nombreStr);
                hito.setApellido(apellidoStr);
                hito.setEquipo(equipoStr);
                hito.setHitoTexto(hitoStr);
                ref.push().setValue(hito);

                Intent intent = new Intent(AdminActivity.this,Activity_UserHitos.class);
                startActivity(intent);
            }
        });


    }

}