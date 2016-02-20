package com.jennifer.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.jennifer.R;
import com.jennifer.service.CrearPrivadasService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by felix on 07/02/2016.
 */
public class CrearPrivadas extends AppCompatActivity implements View.OnClickListener  {

    /*Algunas declaraciones*/
    int i;

    String user;
    String respuesta;

    EditText editDateFecha;
    EditText editTimeHora;
    EditText editTextTitulo;
    EditText editTextDescripcion;
    EditText editTextPremio;
    EditText editTextUsers;
    EditText editTextRespuestas;
    Spinner spinnerTipoApuesta;
    Button buttonAddUsuarios;
    Button buttonAddRespuestas;
    Calendar c = Calendar.getInstance();
    List<String> usuarios = new ArrayList<String>();
    List<String> respuestas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); /*Funciones por default*/
        setContentView(R.layout.activity_crear_privadas); /*Funciones por default*/

        initializeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  /*Funciones por default*/
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { /*Funciones por default*/
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        editDateFecha = (EditText) findViewById(R.id.editDateFecha);
        editTimeHora = (EditText) findViewById(R.id.editTimeHora);
        editTextTitulo = (EditText) findViewById(R.id.editTextTitulo);
        editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        editTextPremio = (EditText) findViewById(R.id.editTextPremio);
        spinnerTipoApuesta = (Spinner) findViewById(R.id.spinnerTipoApuesta);
        editTextUsers = (EditText) findViewById(R.id.editTextUsers);
        editTextRespuestas = (EditText) findViewById(R.id.editTextRespuestas);

        buttonAddUsuarios = (Button) findViewById(R.id.buttonAddUsuarios);
        buttonAddRespuestas = (Button) findViewById(R.id.buttonAddRespuestas);

        buttonAddUsuarios.setOnClickListener(this);
        buttonAddRespuestas.setOnClickListener(this);

        setCurrentDateOnView();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setCurrentDateOnView();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            c.set(Calendar.HOUR, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            setCurrentDateOnView();
        }
    };

    public void dateOnClick(View view) {
        hideSoftKeyboard(CrearPrivadas.this);
        new DatePickerDialog(CrearPrivadas.this, date, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void timeOnClick(View view) {
        hideSoftKeyboard(CrearPrivadas.this);
        new TimePickerDialog(CrearPrivadas.this, time, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
    }

    public void setCurrentDateOnView() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editDateFecha.setText(sdf.format(c.getTime()));

        String timeFormat = "hh:mm a";
        SimpleDateFormat stf = new SimpleDateFormat(timeFormat, Locale.US);
        editTimeHora.setText(stf.format(c.getTime()));
    }

    /*Esconde el teclado*/
    public void hideSoftKeyboard(CrearPrivadas activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(CrearPrivadas.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    protected void insertToDataBase(String titulo, String descripcion, String fecha, String hora, int tipoApuesta2, Double premio, List<String> usuarios, List<String> respuestas) {
        CrearPrivadasService cps = new CrearPrivadasService(CrearPrivadas.this, titulo, descripcion, fecha, hora, tipoApuesta2, premio, usuarios, respuestas);
        cps.execute();
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonInsertar:

                int bandera = 1;

                editTextTitulo = (EditText) findViewById(R.id.editTextTitulo);
                editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
                editDateFecha = (EditText) findViewById(R.id.editDateFecha);
                editTimeHora = (EditText) findViewById(R.id.editTimeHora);
                spinnerTipoApuesta = (Spinner) findViewById(R.id.spinnerTipoApuesta);
                editTextPremio = (EditText) findViewById(R.id.editTextPremio);

                editTextUsers = (EditText) findViewById(R.id.editTextUsers);
                editTextRespuestas = (EditText) findViewById(R.id.editTextRespuestas);

                String titulo = editTextTitulo.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String fecha = editDateFecha.getText().toString();
                String hora = editTimeHora.getText().toString();
                String tipoApuesta = spinnerTipoApuesta.getSelectedItem().toString();
                Double premio = 0.0;
                int tipoApuesta2 = 0;

                if(tipoApuesta.equals("Pre-Apuesta")){
                    tipoApuesta2 = 0;
                }else{
                    tipoApuesta2 = 1;
                }

                if (!editTextPremio.getText().toString().equals("")) {
                    premio = Double.parseDouble(editTextPremio.getText().toString());
                }

                if (titulo.equals("")) {
                    editTextTitulo.setError("Invalid");
                    bandera = 0;
                }

                if (descripcion.equals("")) {
                    editTextDescripcion.setError("Invalid");
                    bandera = 0;
                }

                if (usuarios.size() == 0) {
                    editTextUsers.setError("Ingrese algún usuario");
                    bandera = 0;
                }

                if (respuestas.size() == 0) {
                    editTextRespuestas.setError("Ingrese alguna respuesta");
                    bandera = 0;
                }

                if (premio == 0.0) {
                    editTextPremio.setError("Invalid");
                    bandera = 0;
                }

                if (bandera == 1) {
                    insertToDataBase(titulo, descripcion, fecha, hora, tipoApuesta2, premio, usuarios, respuestas);
                }
                break;

            case R.id.buttonAddUsuarios:

                final Context context = this;
                Intent intent = new Intent(context, InvitarContactosController.class);
                startActivity(intent);
                //i = 1;
                user = editTextUsers.getText().toString();
                //System.out.println(user);
                usuarios.add(user);
                editTextUsers.getText().clear();

                /*for (String row : usuarios) {
                    System.out.println("Nombre " + i + ": " + row);
                    i++;
                }*/
                break;

            case R.id.buttonAddRespuestas:
                //i = 1;
                respuesta = editTextRespuestas.getText().toString();
                //System.out.println(respuesta);
                respuestas.add(respuesta);
                editTextRespuestas.getText().clear();

                /*for (String row : respuestas) {
                    System.out.println("Respuesta: " + i + ": " + row);
                    i++;
                }*/
                break;
        }
    }
}