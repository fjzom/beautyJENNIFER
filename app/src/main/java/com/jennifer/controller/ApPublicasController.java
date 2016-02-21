package com.jennifer.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jennifer.R;
import com.jennifer.adapter.ApPublicasAdapter;
import com.jennifer.model.ApPublicaCompetidor;
import com.jennifer.model.ApPublicas;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class ApPublicasController extends AppCompatActivity {
    private int compElegido = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apuestas_publicas);
        if (isNetworkAvailable()) {
            new ListLoad(this).execute((Void) null);
        } else {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show();
            super.onPause();
            super.finish();
        }

    }
    /* Clase interna que se utiliza para obtener la lista completa de apuestas publicas disponibles */
    public class ListLoad extends AsyncTask<Void, Void, Boolean> {
        private ApPublicas[] jsonList;
        private Context contexto;
        private ProgressDialog progress;

        public ListLoad(Context context) {
            super();
            this.contexto = context;
        }

        @Override
        public void onPreExecute() {
            progress = ProgressDialog.show(contexto, "Apuestas", "Cargando Apuestas, espere...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://excellentprogrammers.esy.es/Script/apPublicas/apPublic.php?TFX=GLP").build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();

                Gson gson = new Gson();

                Type collectionType = new TypeToken<Collection<ApPublicas>>() {
                }.getType();
                Collection<ApPublicas> enums = gson.fromJson(result, collectionType);
                jsonList = enums.toArray(new ApPublicas[enums.size()]);
            } catch (IOException e) {
                e.getStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            ApPublicasAdapter adapter = new ApPublicasAdapter(this.contexto, R.layout.content_apuestas_publicas, this.jsonList);
            ListView listaPublicas = (ListView) findViewById(R.id.listView_apPub);
            listaPublicas.setAdapter(adapter);
            progress.dismiss();
            listaPublicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new CompetitorLoad(contexto, jsonList[position].getId()).execute((Void) null);
                }
            });
        }
    }

    /* Funcion que revisa si el movil tiene red */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* Funcion que muestra una ventana de dialogo para hacer la apuesta */
    protected void showInputDialog(final ApPublicaCompetidor[] competitorList,final Context contexto, final int idApuesta) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.dialog_apuestas_publicas, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.montoBet);
        final Spinner spinner = (Spinner) promptView.findViewById(R.id.spinnerCompetitor);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        double montoApuesta =  Double.parseDouble(editText.getText().toString());
                        procesaDatos(compElegido, idApuesta, montoApuesta);
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

        ArrayAdapter<ApPublicaCompetidor> adap = new ArrayAdapter<ApPublicaCompetidor>(this, android.R.layout.simple_spinner_item, competitorList);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adap);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                compElegido  = competitorList[pos].getId();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /* Clase interna que se utiliza para obtener la lista de los competidores de la apuesta seleccionada */
    public class CompetitorLoad extends AsyncTask<Void, Void, Boolean> {
        private ApPublicaCompetidor[] jsonCompetitor;
        private Context contexto;
        private int matchId;
        private ProgressDialog progress;

        public CompetitorLoad(Context context, int matchId) {
            super();
            this.contexto = context;
            this.matchId = matchId;
        }

        @Override
        public void onPreExecute() {
            progress = ProgressDialog.show(contexto, "Competidores", "Cargando Competidores, espere...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://excellentprogrammers.esy.es/Script/apPublicas/apPublic.php?TFX=GLC&MTCH="+(this.matchId+1)).build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();

                Gson gson = new Gson();

                Type collectionType = new TypeToken<Collection<ApPublicaCompetidor>>() {
                }.getType();
                Collection<ApPublicaCompetidor> enums = gson.fromJson(result, collectionType);
                jsonCompetitor = enums.toArray(new ApPublicaCompetidor[enums.size()]);
            } catch (IOException e) {
                e.getStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progress.dismiss();
            showInputDialog(jsonCompetitor, contexto, this.matchId);
        }
    }

    /* Funcion que procesa la apuesta seleccionada para participar */
    private void procesaDatos(int compElegido, int idBet, double montoApuesta){
        int idUsuario = 1;
        int idApuesta = idBet;
        int compSelec = compElegido;
        double qtyApostada = montoApuesta;

        StringBuilder datos = new StringBuilder();
        datos.append("&idUser="+idUsuario);
        datos.append("&idApu="+idApuesta);
        datos.append("&comSel="+compElegido);
        datos.append("&qty="+qtyApostada);

        new ParticiparApuesta(this, datos.toString()).execute((Void) null);
    }

    /* Clase interna que se utiliza para procesar la apuesta seleccionada para participar */
    public class ParticiparApuesta extends AsyncTask<Void, Void, Boolean> {
        private ApPublicaCompetidor[] jsonCompetitor;
        private Context contexto;
        private ProgressDialog progress;
        String datos = new String();

        public ParticiparApuesta(Context context, String datos) {
            super();
            this.contexto = context;
            this.datos = datos;
        }

        @Override
        public void onPreExecute() {
            progress = ProgressDialog.show(contexto, "Procesando", "Procesando su elección, espere...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://excellentprogrammers.esy.es/Script/apPublicas/apPublic.php?TFX=PAP"+datos).build();
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.getStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progress.dismiss();
            Toast.makeText(contexto, "Apuesta completada", Toast.LENGTH_LONG).show();
        }
    }

}
