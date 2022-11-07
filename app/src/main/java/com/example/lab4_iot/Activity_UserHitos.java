package com.example.lab4_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lab4_iot.adapters.ListaHitosAdapter;
import com.example.lab4_iot.entity.Hito;
import com.example.lab4_iot.entity.Jugador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_UserHitos extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    ArrayList<Hito> listaHitos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_hitos);


        ListaHitosAdapter adapter = new ListaHitosAdapter();


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Alianza Lima");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren() ){
                    Hito hito = children.getValue(Hito.class);
                    listaHitos.add(hito);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        adapter.setListaHitos(listaHitos);
        adapter.setContext(Activity_UserHitos.this);
        RecyclerView recyclerView = findViewById(R.id.recycleViewer);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_UserHitos.this));

    }
}