package com.jennifer.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alex on 14/01/2016.
 */
public class User {

    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private String email;
    private String password;
    private String description;
    private int imagen;

    public User(String description, int imagen) {
        this.setDescription(description);
        this.setImagen(imagen);
    }

    public User(JSONObject json) {
        try {
            this.setEmail(json.getString(getEMAIL()));
            this.setPassword(json.getString(getPASSWORD()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
