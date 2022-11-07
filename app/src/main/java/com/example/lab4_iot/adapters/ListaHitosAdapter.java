package com.example.lab4_iot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_iot.R;
import com.example.lab4_iot.entity.Hito;

import java.util.ArrayList;

public class ListaHitosAdapter extends RecyclerView.Adapter<ListaHitosAdapter.hitoViewHolder> {

    private ArrayList<Hito> listaHitos;
    private Context context;

    public void setContext(Context context){
        this.context = context;
    }

    public void setListaHitos(ArrayList<Hito> listaHitos){
        this.listaHitos = listaHitos;
    }

    public class hitoViewHolder extends RecyclerView.ViewHolder {
        Hito hito;

        public hitoViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }


    @NonNull
    @Override
    public hitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycle_hitos,parent,false);
        return new hitoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull hitoViewHolder holder, int position) {
        Hito h = listaHitos.get(position);
        TextView textJugador =holder.itemView.findViewById(R.id.textJugador);
        TextView textHito = holder.itemView.findViewById(R.id.textHito);
        String sJugador = "Jugador: "+ h.getNombre()+" " +h.getApellido()+"\nEquipo: "+h.getEquipo();
        String sHito = h.getHito();

        textJugador.setText(sJugador);
        textHito.setText(sHito);

    }

    @Override
    public int getItemCount() {
        return listaHitos.size();
    }
}
