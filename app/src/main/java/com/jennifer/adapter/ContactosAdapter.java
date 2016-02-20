package com.jennifer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jennifer.R;
import com.jennifer.model.Contactos;

import java.util.ArrayList;

/**
 * Created by Felipe Oviedo on 2/11/2016.
 */
public class ContactosAdapter extends ArrayAdapter<Contactos> {

    private Context contexto;
    private int layoutId;
    public Contactos[] contactosList = null;

    public ContactosAdapter(Context context, int textViewResourceId, Contactos[] contactosList ){
        super(context, textViewResourceId, contactosList);


        this.contactosList = contactosList;
		this.contexto = context;
		this.layoutId = textViewResourceId;
    }


    private class ViewHolder { 
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.content_activity_invitar_contactos, null);

            holder = new ViewHolder(); 
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;

                    Contactos contactos = (Contactos) cb.getTag();
                    Toast.makeText(getContext().getApplicationContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    contactos.setSelected(cb.isChecked());
                }
            });

    }
    else {
        holder = (ViewHolder) convertView.getTag();
    }
        Contactos contactos = contactosList[position];
        holder.name.setText(contactos.getName());
        holder.name.setChecked(contactos.isSelected());
        holder.name.setTag(contactos);


        return convertView;


    }
}
