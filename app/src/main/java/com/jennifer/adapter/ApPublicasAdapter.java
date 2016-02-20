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

/**
 * Created by Juan Rdz on 06/02/2016.
 */
public class ApPublicasAdapter extends ArrayAdapter<ApPublicas>{
    private Context contexto;
    private int layoutId;
    private ApPublicas[] datos = null;

    public ApPublicasAdapter(Context context, int layoutId, ApPublicas[] data){
        super(context, layoutId, data);

        this.contexto = context;
        this.layoutId = layoutId;
        this.datos = data;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ApPublicasHolder holder = null;

        if(row==null){
            LayoutInflater inflater = ((Activity)contexto).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new ApPublicasHolder();
            holder.nombreApuesta = (TextView) row.findViewById(R.id.nombre_apuesta);
            holder.descripcionApuesta = (TextView) row.findViewById(R.id.descripcion_apuesta);
            row.setTag(holder);
        }else{
            holder = (ApPublicasHolder) row.getTag();
        }

        ApPublicas apPublicas = datos[position];
        holder.nombreApuesta.setText(apPublicas.getName());
        holder.descripcionApuesta.setText(apPublicas.getDescription());
        return row;
    }

    /* Clase interna que mantiene los datos*/
    static class ApPublicasHolder{
        ImageView imagen; //TODO hay que sacar la imagen de la base de datos
        TextView nombreApuesta;
        TextView descripcionApuesta;
    }
}
