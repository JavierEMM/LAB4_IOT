package com.example.lab4_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        String[] equipos = {"Alianza Lima","Universitario de Deportes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,equipos);
        Spinner spinnerEquipos = findViewById(R.id.spinnerEquipo);
        spinnerEquipos.setAdapter(adapter);

        String[] nombres = {"Luis","Pedro"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,nombres);
        Spinner spinnerNombre = findViewById(R.id.spinnerNombre);
        spinnerNombre.setAdapter(adapter1);

        String[] apellidos = {"Diaz","Perez"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,apellidos);
        Spinner spinnerApellido = findViewById(R.id.spinnerApellido);
        spinnerApellido.setAdapter(adapter2);

    }


}