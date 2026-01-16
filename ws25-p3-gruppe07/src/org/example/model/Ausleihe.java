package org.example.model;

import java.io.Serializable;

// Königsklasse, führt IDs zusammen
public class Ausleihe implements Serializable {
    private int ausleihID;
    private int exemplarID;
    private int nutzerID;

    public Ausleihe(int ausleihID, int exemplarID, int nutzerID) {
        this.ausleihID = ausleihID;
        this.exemplarID = exemplarID;
        this.nutzerID = nutzerID;
    }

    public String toCSV() {
        return ausleihID + ";" + exemplarID + ";" + nutzerID + ";";
    }

    // Getter
    public int getAusleihID() { return ausleihID; }
    public int getExemplarID() { return exemplarID; }
    public int getNutzerID() { return nutzerID; }
}