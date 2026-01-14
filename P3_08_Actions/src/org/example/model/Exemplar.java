package org.example.model;

public class Exemplar {
    private int exemplarID;
    private String isbn; // Verweis zum Buch
    private String standort; // z.B. "Regal A1"
    private boolean ausleihbar; // Pflicht: Boolean-Attribut [cite: 13]
    private int anzahlAusleihen; // Pflicht: Ganzzahl-Attribut [cite: 11]

    public Exemplar(int exemplarID, String isbn, String standort) {
        this.exemplarID = exemplarID;
        this.isbn = isbn;
        this.standort = standort;
        this.ausleihbar = true;
        this.anzahlAusleihen = 0;
    }

    // Getter und Setter
    public int getExemplarID() { return exemplarID; }
    public String getIsbn() { return isbn; }
}