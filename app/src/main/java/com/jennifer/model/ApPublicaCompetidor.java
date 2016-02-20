package com.jennifer.model;

/**
 * Created by Juan Rdz on 06/02/2016.
 */
public class ApPublicaCompetidor{
    private String Id;
    private String Description;

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getId() {
        return Id;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
