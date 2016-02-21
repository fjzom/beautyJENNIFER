package com.jennifer.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jennifer.adapter.ContactosAdapter;
import com.jennifer.adapter.InfoApuestasAdapter;
import com.jennifer.R;
import com.jennifer.model.ApPublicaCompetidor;
import com.jennifer.model.Contactos;
import com.jennifer.model.InfoApuesta;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by felix on 20/02/2016.
 */
public class InfoApuestasController  extends AppCompatActivity {

    InfoApuestasAdapter dataAdapter = null;


    protected void OnCreate (Bundle savedInstancecState ){
        super.onCreate(savedInstancecState);
        setContentView(R.layout.popup_info_apuestas);
        new Load(this).execute((Void) null);

    }

    public class Load extends AsyncTask<Void, Void , Boolean>{

        private InfoApuesta  json;
        private Context contexto;

        public Load(Context context) {
            super();
            this.contexto = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://excellentprogrammers.esy.es/Script/infoApuesta/consulta.php?TFX=GLP").build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();

                Gson gson = new Gson();

                Type split = new TypeToken<InfoApuesta>() {}.getType();
                InfoApuesta enums = gson.fromJson(result, split);
                json = enums;
            } catch (IOException e) {
                e.getStackTrace();
            }
            return true;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            showInputDialog(json);
/*
            ArrayList<Integer> x = new ArrayList<>();
            Integer r = 1;
            x.add(r);

            x.get()

            */
        }


        protected void showInputDialog(InfoApuesta json) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.contexto);
            View promptView = layoutInflater.inflate(R.layout.popup_info_apuestas, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.contexto);




            final TextView acumulado = (TextView) promptView.findViewById(R.id.textView10);
            final TextView cantApuestas = (TextView) promptView.findViewById(R.id.textView11);
            final TextView Quantity = (TextView) promptView.findViewById(R.id.textView12);



            acumulado.setText(json.getAcumulado());
            cantApuestas.setText(json.getCantApuestas());
            Quantity.setText((int) json.getQuantity());

            alertDialogBuilder.setView(promptView);

            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO aqui va lo que va a suceder cuando se de aceptar al dialogo
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

        }


    }
}
