package com.example.lab4_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lab4_iot.entity.Jugador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Activity_UserAlineacion extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    ArrayList<TextView> listaPosiciones;
    ArrayList<Jugador> listaEquipo1 = new ArrayList<>();
    ArrayList<Jugador> listaEquipo2 = new ArrayList<>();
    String equipo1= "Alianza Lima";
    String equipo2 = "Universitario de Deportes";
    TextView textEquipo;

    int estado=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_alineacion);
        firebaseDatabase = FirebaseDatabase.getInstance();

        textEquipo = (TextView) findViewById(R.id.textEquipo);
        listaPosiciones = new ArrayList<>(Arrays.asList(findViewById(R.id.nombrePost1),
                findViewById(R.id.nombrePost2),
                findViewById(R.id.nombrePost3),
                findViewById(R.id.nombrePost4),
                findViewById(R.id.nombrePost5),
                findViewById(R.id.nombrePost6),
                findViewById(R.id.nombrePost7),
                findViewById(R.id.nombrePost8),
                findViewById(R.id.nombrePost9),
                findViewById(R.id.nombrePost10),
                findViewById(R.id.nombrePost11)));

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Jugadores");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren() ){
                    Jugador jugador = children.getValue(Jugador.class);
                    if(jugador.getEquipo().equals(equipo1)){
                        listaEquipo1.add(jugador);
                    } else{
                        listaEquipo2.add(jugador);
                    }
                    ArrayList<Jugador> listaEquipo;
                    String equipo;

                    if(estado==0){
                        listaEquipo=listaEquipo1;
                        equipo=equipo1;
                    } else{
                        listaEquipo = listaEquipo2;
                        equipo=equipo2;
                    }
                    int last=0;
                    for(int i=0; i<listaEquipo.size(); i++){
                        Jugador jugadortemp = listaEquipo.get(i);
                        String nombre = jugadortemp.getNombre() + "\n"+jugadortemp.getApellido();
                        listaPosiciones.get(i).setText(nombre);
                        last=i;
                    }
                    for(int i=last+1; i<listaPosiciones.size(); i++){
                        String nombre = "Sin definir";
                        listaPosiciones.get(i).setText(nombre);
                    }
                    textEquipo.setText(equipo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });



        for(int i=0; i<listaEquipo1.size(); i++){
            Jugador jugador = listaEquipo1.get(i);
            String nombre = jugador.getNombre() + "\n"+jugador.getApellido();
            listaPosiciones.get(i).setText(nombre);
        }
        textEquipo.setText(equipo1);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_alineacion,menu);
        return true;
    }

    public void listarOtroEquipo(MenuItem item){
        estado++;
        estado= estado%2;
        ArrayList<Jugador> listaEquipo;
        String equipo;
        if(estado==0){
            listaEquipo=listaEquipo1;
            equipo=equipo1;
        } else{
            listaEquipo = listaEquipo2;
            equipo=equipo2;
        }
        for(int i=0; i<listaEquipo.size(); i++){
            Jugador jugador = listaEquipo.get(i);
            String nombre = jugador.getNombre() + "\n"+jugador.getApellido();
            listaPosiciones.get(i).setText(nombre);
        }
        textEquipo.setText(equipo);
    }
}