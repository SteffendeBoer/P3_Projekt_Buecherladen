package org.example.logic;

import org.example.model.*;
import org.example.data.CSVDataStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BibliothekService {
    // 1. Die Listen für den Arbeitsspeicher
    private List<Buch> alleBuecher;
    private List<Nutzer> alleNutzer;
    private List<Exemplar> alleExemplare;
    private List<Ausleihe> alleAusleihen;

    private CSVDataStream csvHandler;

    public BibliothekService() {
        this.csvHandler = new CSVDataStream();
        
        // Initialisieren der Listen und Laden der Daten von der Festplatte
        datenLaden();
    }

    private void datenLaden() {
        try {
            this.alleBuecher = csvHandler.ladeBuecher();
            this.alleNutzer = csvHandler.ladeNutzer();
            this.alleExemplare = csvHandler.ladeExemplare();
            this.alleAusleihen = csvHandler.ladeAusleihe();
        } catch (IOException e) {
            // Falls Dateien fehlen, leere Listen erstellen
            this.alleBuecher = new ArrayList<>();
            this.alleNutzer = new ArrayList<>();
            this.alleExemplare = new ArrayList<>();
            this.alleAusleihen = new ArrayList<>();
            System.err.println("Hinweis: Neue Datenbank erstellt (Dateien noch nicht vorhanden).");
        }
    }

    // --- METHODEN FÜR BÜCHER ---
    public void addBuch(Buch b) {
        alleBuecher.add(b);
        speichernBuecher();
    }
    private void speichernBuecher() {
        try { csvHandler.speichereBuecher(alleBuecher); } catch (IOException e) { e.printStackTrace(); }
    }
    public void entferneBuch(Buch b) {
        alleBuecher.remove(b);
        speichernBuecher(); // Aktualisiert die buecher.csv
    }
    public List<Buch> getAlleBuecher() { return alleBuecher; }

    public List<Buch> searchBuchs(String query) {
        List<Buch> treffer = new ArrayList<>();
        String q = query.toLowerCase();
        for (Buch b : alleBuecher) {
            // Suche in Titel, ISBN oder Autor
            if (b.getTitel().toLowerCase().contains(q) || 
                b.getIsbn().toLowerCase().contains(q) || 
                b.getAutor().toLowerCase().contains(q)) {
                treffer.add(b);
            }
        }
        return treffer;
    }
    // In BibliothekService.java hinzufügen
    public void speichernAlleDaten() {
        try {
            csvHandler.speichereBuecher(alleBuecher);
            System.out.println("Bücher erfolgreich gespeichert.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Fehler beim Speichern der Bücher.");
        }
    }

    // --- METHODEN FÜR NUTZER ---
    public void addNutzer(Nutzer n) {
        alleNutzer.add(n);
        speichernNutzer();
    }
    private void speichernNutzer() {
        try { csvHandler.speichereNutzer(alleNutzer); } catch (IOException e) { e.printStackTrace(); }
    }
    public void entferneNutzer(Nutzer n) {
        alleNutzer.remove(n);
        speichernNutzer(); // Aktualisiert die nutzer.csv
    }
    public List<Nutzer> getAlleNutzer() { return alleNutzer; }

    public List<Nutzer> searchNutzer(String query) {
        List<Nutzer> treffer = new ArrayList<>();
        String searchLower = query.toLowerCase();
        
        for (Nutzer n : alleNutzer) {
            // 1. Die ID in Text umwandeln
            String idAlsText = String.valueOf(n.getNutzerID());

            // Suche Vorname, nachname oder Email
            if (idAlsText.contains(searchLower) || 
                n.getVorname().toLowerCase().contains(searchLower) || 
                n.getNachname().toLowerCase().contains(searchLower)) {
                treffer.add(n);
            }
        }
        return treffer;
    }

    // --- METHODEN FÜR EXEMPLARE ---
    public void addExemplar(Exemplar e, Buch b) {
        if (e != null && b != null) {
            // Prüfung des Aktivitätsstatus
            if (!b.IstVerfuegbar()) {
                throw new IllegalStateException("Das Buch ist als 'nicht verfügbar' markiert.");
            }
            
            alleExemplare.add(e);
            speichernExemplare(); // Schreibt in die exemplar.csv
        }
    }
    private void speichernExemplare() {
        try { csvHandler.speichereExemplare(alleExemplare); } catch (IOException e) { e.printStackTrace(); }
    }
    public void entferneExemplar(Exemplar e) {
        alleExemplare.remove(e);
        speichernExemplare(); // Aktualisiert die exemplar.csv
    }
    public void ausleiheBeenden(Ausleihe a) {
        if (a == null) return;
        
        // 1. Das verknüpfte Exemplar suchen
        for (Exemplar ex : alleExemplare) {
            if (ex.getExemplarID() == a.getExemplarID()) {
                // Wieder verfügbar machen
                ex.setIstVerleihbar(true); 
                break;
            }
        }

        // 2. Die Ausleihe aus der Liste löschen
        alleAusleihen.remove(a);

        // 3. Beides speichern, damit der Status "Verfügbar" in der CSV landet
        try {
            csvHandler.speichereAusleihe(alleAusleihen);
            csvHandler.speichereExemplare(alleExemplare);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public List<Exemplar> getAlleExemplare() { return alleExemplare; }

    // --- METHODEN FÜR AUSLEIHEN ---
    public void addAusleihe(Ausleihe a) {
        if (a != null) { // Sicherheitscheck
            alleAusleihen.add(a);
            speichernAusleihen();
        }
    }
    private void speichernAusleihen() {
        try { csvHandler.speichereAusleihe(alleAusleihen); } catch (IOException e) { e.printStackTrace(); }
    }
    public void entferneAusleihe(Ausleihe a) {
        alleAusleihen.remove(a);
        speichernAusleihen(); // Aktualisiert die ausleihe.csv
    }

    public void ausleiheDurchfuehren(Ausleihe a, Exemplar ex) {
        if (a != null && ex != null) {
            // 1. Ausleihe zur Liste hinzufügen
            alleAusleihen.add(a);
            
            // 2. Status des konkreten Exemplars auf "nicht verfügbar" setzen
            ex.setIstVerleihbar(false); 
            
            // 3. Beide Listen dauerhaft speichern
            try {
                csvHandler.speichereAusleihe(alleAusleihen);
                csvHandler.speichereExemplare(alleExemplare);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Ausleihe> searchAusleihen(String query) {
        List<Ausleihe> treffer = new ArrayList<>();
        String q = query.toLowerCase();
        
        for (Ausleihe a : alleAusleihen) {
            // Suche nach Ausleih-ID, Exemplar-ID oder Nutzer-ID
            if (String.valueOf(a.getAusleihID()).contains(q) || 
                String.valueOf(a.getExemplarID()).contains(q) || 
                String.valueOf(a.getNutzerID()).contains(q)) {
                treffer.add(a);
            }
        }
        return treffer;
    }

    public List<Ausleihe> getAlleAusleihen() { return alleAusleihen; }
}
