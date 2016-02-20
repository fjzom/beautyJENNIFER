package com.jennifer.model;

/**
 * Created by mipc on 2/11/2016.
 */
public class Contactos {
 
    String Name = null;
    boolean selected = false;


    public Contactos(String code, String Name, boolean selected) {
        super(); 
        this.Name = Name;
        this.selected = selected;

    }
 
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
