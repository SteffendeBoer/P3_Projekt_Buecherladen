// File: Book.java
package org.example.model;

public class Buch {
    // Bezeichnungen, nutzbar für private oder offentliche Bibliothek
    private String titel;
    private String isbn;
    private String autor;
    private String sprache;
    private int seitenanzahl;
    private int erscheinungsjahr;
    private double preis;
    // Gestrichen keien öffentliche Bibliothek
    // private double istVerfuegbar

    // Konstruktor
    public Buch(String titel, String isbn, String autor, String sprache, int seitenanzahl,
                int erscheinungsjahr, double preis) {
        this.titel = titel;
        this.isbn = isbn;
        this.autor = autor;
        this.seitenanzahl = seitenanzahl;
        this.erscheinungsjahr = erscheinungsjahr;
        this.preis = preis;
        this.sprache = sprache;
    }
    
    // Getter/Setter
    public String getTitel(){ // Entspricht dem alten getName()
        return this.titel;
    }
    
    public String getIsbn(){ // Entspricht dem alten getId()
        return this.isbn;
    }
    public String getAutor(){
        return this.autor;
    }
    public String getSprache(){
        return this.sprache;
    }
    public int getSeitenzahl(){
        return this.seitenanzahl;
    }
    public int getErscheinungsjahr(){
        return this.erscheinungsjahr;
    }
    public double getPreis(){
        return this.preis;
    }
    
    @Override
    public String toString(){
        // Anpassung des Anzeigetextes
        return this.titel + " (ISBN: " + this.isbn + ")"; 
    }
    // Datenspeicherung
    public String toCSV() {
        // Trennung durch Semikolon
        return titel + ";" + isbn + ";" + autor + ";" + seitenanzahl + ";" + preis;
    }
    
}