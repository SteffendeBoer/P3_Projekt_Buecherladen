package org.example.model;

public class Nutzer{
    private int nutzerID;
    private String vorname;
    private String nachname;
    private String email;
    private String wohnort;
    private String rolle; // Könnte irrelevant seien, wegem Scope
    private boolean istAktiv; 

    public Nutzer(int nutzerID, String vorname, String nachname, String email, String wohnort, String rolle) {
        this.nutzerID = nutzerID;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.wohnort = wohnort;
        this.rolle = rolle;
        this.istAktiv = true;
    }

    // Getter/Setter
    public int getNutzerID(){ // Entspricht dem alten getName()
        return this.nutzerID;
    }
    public String getVorname(){
        return this.vorname;
    }
    public String getNachname(){
        return this.nachname;
    }
    public String getEmail(){
        return this.email;
    }
    public String getWohnort(){
        return this.wohnort;
    }
    public String getRolle(){
        return this.rolle;
    }
    public boolean getIstAktiv(){
        return this.istAktiv;
    }

    // Wandelt das Objekt in eine CSV-Zeile um
    public String toCSV() {
        return nutzerID + ";" + vorname + ";" + nachname + ";" + email + ";" + wohnort + ";" + rolle + ";" + istAktiv;
    }
}