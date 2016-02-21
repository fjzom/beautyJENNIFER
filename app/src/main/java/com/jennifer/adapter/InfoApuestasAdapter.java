package com.jennifer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jennifer.R;
import com.jennifer.model.ApPublicas;
import com.jennifer.model.InfoApuesta;

/**
 * Created by felix on 20/02/2016.
 */
public class InfoApuestasAdapter extends InfoApuesta {

    private  Context context;
    private int layoutId;
    public InfoApuesta infoApuesta = null;

    public InfoApuestasAdapter(Context context, int textViewResourceId, InfoApuesta infoApuesta) {


        this.context = context;
        this.layoutId = textViewResourceId;
        this.infoApuesta = infoApuesta;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        InfoApuestaHolder holder = null;

        if(row==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new InfoApuestaHolder();
            holder.acumulado = (TextView) row.findViewById(R.id.textView10);
            holder.cantApuestas = (TextView) row.findViewById(R.id.textView11);
            holder.Quantity = (TextView) row.findViewById(R.id.textView12);
            row.setTag(holder);
        }else{
            holder = (InfoApuestaHolder) row.getTag();
        }


        infoApuesta.getQuantity();
        InfoApuesta infoApuesta = this.infoApuesta;
        holder.acumulado.setText(infoApuesta.getAcumulado());
        holder.cantApuestas.setText(infoApuesta.getCantApuestas());
        holder.Quantity.setText((int) infoApuesta.getQuantity());
        return row;
    }

    /* Clase interna que mantiene los datos*/
    static class InfoApuestaHolder{
        TextView acumulado;
        TextView cantApuestas;
        TextView Quantity;
    }
}

