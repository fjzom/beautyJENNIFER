package com.jennifer.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jennifer.R;
import com.jennifer.adapter.UsuariosApuestasPrivadaAdapter;
import com.jennifer.connection.ServerConnection;
import com.jennifer.model.UsuarioApuestaPrivada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 07/02/2016.
 */
public class UsuariosApuestasPrivadasService extends AsyncTask<Void, Void, Boolean> {

    private final String URL = "http://excellentprogrammers.esy.es/Script/UsuarioApuestaPrivada/UsuarioApuestaPrivada.php";
    private final String TAG_ARRAY_PRIVADA = "apuesta_array";
    private final String TAG_ID = "id";
    private final String TAG_NAME = "name";
    private final String TAG_DESCRIPTION = "description";
    private int type;
    private FragmentActivity fragmentActivity;
    private View view;
    private ProgressDialog progress;
    private List<UsuarioApuestaPrivada> items;

    public UsuariosApuestasPrivadasService(FragmentActivity fragmentActivity, View view, int type) {
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... args) {
        StringBuilder params = new StringBuilder();
        JSONArray JSONArray;
        ServerConnection serverConnection = new ServerConnection();
        JSONObject json;
        JSONObject instance;
        UsuarioApuestaPrivada apuestaPrivada;

        params.append("type").append("=").append(type).append("&")
                .append("id").append("=").append(1);

        json = serverConnection.makeHttpRequestPost(URL, params.toString());
        items = new ArrayList<>();

        try {
            JSONArray = json.getJSONArray(TAG_ARRAY_PRIVADA);

            for (int i = 0; i < JSONArray.length(); i++) {
                instance = JSONArray.getJSONObject(i);
                apuestaPrivada = new UsuarioApuestaPrivada();
                apuestaPrivada.setId(instance.getInt(TAG_ID));
                apuestaPrivada.setName(instance.getString(TAG_NAME));
                apuestaPrivada.setDescription(instance.getString(TAG_DESCRIPTION));
                items.add(apuestaPrivada);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean file_url) {
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));
        recyclerView.setAdapter(new UsuariosApuestasPrivadaAdapter(items));
        recyclerView.setHasFixedSize(true);
    }
}
