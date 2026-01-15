package org.example.model;

import java.io.Serializable;

public class Exemplar implements Serializable {
    private int exemplarID;
    private String isbn;
    private String standort;
    private boolean istVerleihbar; // Pflicht: Boolean
    private int zustandNote;       // Pflicht: Ganzzahl (z.B. 1=Neu, 5=Beschädigt)

    public Exemplar(int exemplarID, String isbn, String standort, boolean istVerleihbar, int zustandNote) {
        this.exemplarID = exemplarID;
        this.isbn = isbn;
        this.standort = standort;
        this.istVerleihbar = istVerleihbar;
        this.zustandNote = zustandNote;
    }

    public String toCSV() {
        return exemplarID + ";" + isbn + ";" + standort + ";" + istVerleihbar + ";" + zustandNote;
    }

    // Getter
    public int getExemplarID() { return exemplarID; }
    public String getIsbn() { return isbn; }
}