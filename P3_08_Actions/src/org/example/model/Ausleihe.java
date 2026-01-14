package org.example.model;

import java.io.Serializable;

// Königsklasse, führt IDs zusammen
public class Ausleihe implements Serializable {
    private int ausleihID;
    private int exemplarID;
    private int nutzerID;
    private long ausleihDatum;   // Zeitstempel als Zahl
    private long rueckgabeSoll;  // Frist
    private double gebuehr;      // Pflicht: Gleitkomma

    public Ausleihe(int ausleihID, int exemplarID, int nutzerID, long ausleihDatum, long rueckgabeSoll, double gebuehr) {
        this.ausleihID = ausleihID;
        this.exemplarID = exemplarID;
        this.nutzerID = nutzerID;
        this.ausleihDatum = ausleihDatum;
        this.rueckgabeSoll = rueckgabeSoll;
        this.gebuehr = gebuehr;
    }

    public String toCSV() {
        return ausleihID + ";" + exemplarID + ";" + nutzerID + ";" + ausleihDatum + ";" + rueckgabeSoll + ";" + gebuehr;
    }

    // Getter
    public int getAusleihID() { return ausleihID; }
}