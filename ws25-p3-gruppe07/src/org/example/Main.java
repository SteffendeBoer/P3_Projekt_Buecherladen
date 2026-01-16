package org.example;

import org.example.logic.BibliothekService;
import org.example.gui.MainFrame;


public class Main {
    public static void main(String[] args) {
        // 1. Controller/Service starten (lädt CSV-Dateien)
        BibliothekService service = new BibliothekService();
        
        // 2. View starten und Service übergeben
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame(service).setVisible(true);
        });
    }
}