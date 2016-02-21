package com.jennifer.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jennifer.R;
import com.jennifer.adapter.ContactosAdapter;
import com.jennifer.model.Contactos;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;


public class InvitarContactosController extends AppCompatActivity {

    ContactosAdapter dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitar_contactos);

        if (isNetworkAvailable()) {
            new ListLoad(this).execute((Void) null);
        } else {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show();
            super.onPause();
            super.finish();
        }

        checkButtonClick();

    }


    public void onBackPressed() {
        Intent myIntent = new Intent(InvitarContactosController.this, CrearPrivadas.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;
    }

    private void checkButtonClick() {
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("Tus contactos han sido invitados...\n");

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class ListLoad extends AsyncTask<Void, Void, Boolean> {
        private Contactos[] jsonList;
        private Context contexto;
        private ProgressDialog progress;

        public ListLoad(Context context) {
            super();
            this.contexto = context;
        }

        @Override
        public void onPreExecute() {
            progress = ProgressDialog.show(contexto, "Contactos", "Cargando Lista, espere...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://excellentprogrammers.esy.es/Script/listaContactos/listaContactos.php?TFX=GLP").build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();

                Gson gson = new Gson();

                Type collectionType = new TypeToken<Collection<Contactos>>() {
                }.getType();
                Collection<Contactos> enums = gson.fromJson(result, collectionType);
                jsonList = enums.toArray(new Contactos[enums.size()]);
            } catch (IOException e) {
                e.getStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            ContactosAdapter adapter = new ContactosAdapter(this.contexto, R.layout.content_activity_invitar_contactos, this.jsonList);
            ListView listaContactos = (ListView) findViewById(R.id.listView1);
            listaContactos.setAdapter(adapter);
            progress.dismiss();
          /*  listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new CompetitorLoad(contexto, position).execute((Void) null);
                }
            });*/
        }


    }
}
