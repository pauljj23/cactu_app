package com.pauljordan.cactuapp.models;

import java.io.Serializable;

public class AmenazaModel implements Serializable {
    private String Amenaza;
    private String uid;


    public AmenazaModel() {
    }

    public AmenazaModel(String amenaza, String uid) {
        Amenaza = amenaza;
        this.uid = uid;
    }

    public String getAmenaza() {
        return Amenaza;
    }

    public void setAmenaza(String amenaza) {
        Amenaza = amenaza;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
