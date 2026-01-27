package org.example.model;

public class Exemplar {
    private int exemplarID;
    private String isbn;
    private boolean istVerleihbar; // Status der physischen Kopie

    public Exemplar(int exemplarID, String isbn, boolean istVerleihbar) {
        this.exemplarID = exemplarID;
        this.isbn = isbn;
        this.istVerleihbar = istVerleihbar;
    }

    // CSV-Format für die Speicherung im CSVDataStream
    public String toCSV() {
        return exemplarID + ";" + isbn + ";" + istVerleihbar;
    }

    @Override
    public String toString() {
        // Erscheint so in der JComboBox des Ausleihe-Dialogs
        return "Exemplar-ID: " + exemplarID + " [" + (istVerleihbar ? "bereit" : "verliehen") + "]";
    }

    // Getter & Setter
    public int getExemplarID() { return exemplarID; }
    public String getIsbn() { return isbn; }
    
    // Die wichtige Methode für den Filter im Dialog
    public boolean istVerleihbar() { 
        return istVerleihbar; 
    }

    public void setIstVerleihbar(boolean istVerleihbar) { 
        this.istVerleihbar = istVerleihbar; 
    }
}