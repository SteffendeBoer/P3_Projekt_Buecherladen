package org.example.model;

public class Nutzer{
    private int nutzerID;
    private String vorname;
    private String nachname;
    private String email;

    public Nutzer(int nutzerID, String vorname, String nachname, String email) {
        this.nutzerID = nutzerID;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
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

    @Override
    public String toString() {
        return getNachname() + " (Nachname: " + getNachname() + ")";
    }
    // Datenspeicherung
    public String toCSV() {
        // Trennung durch Semikolon
        return nutzerID + ";" + vorname + ";" + nachname + ";" + email;
    }
}