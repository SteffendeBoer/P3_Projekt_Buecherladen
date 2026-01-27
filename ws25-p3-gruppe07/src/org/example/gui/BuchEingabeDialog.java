package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Buch;
import javax.swing.*;
import java.awt.*;

public class BuchEingabeDialog extends JDialog {
    private BibliothekService service;
    
    // Textfelder als Klassenvariablen, damit wir später darauf zugreifen können
    private JTextField txtTitel = new JTextField();
    private JTextField txtIsbn = new JTextField();
    private JTextField txtAutor = new JTextField();
    private JTextField txtSeiten = new JTextField();
    private JTextField txtJahr = new JTextField();
    private JTextField txtPreis = new JTextField();
    private JTextField txtSprache = new JTextField();
    private JCheckBox cbVerfuegbar = new JCheckBox("Ist verfügbar", true);

    public BuchEingabeDialog(Frame owner, BibliothekService service) {
        super(owner, "Neues Buch hinzufügen", true); // true = modal (blockiert Hauptfenster)
        this.service = service;

        setSize(400, 400);
        setLocationRelativeTo(owner);

        // HAUPT-LAYOUT: BorderLayout
        setLayout(new BorderLayout());

        // CENTER: Formular mit GridLayout (8 Zeilen, 2 Spalten)
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Titel:"));
        formPanel.add(txtTitel);
        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(txtIsbn);
        formPanel.add(new JLabel("Autor:"));
        formPanel.add(txtAutor);
        formPanel.add(new JLabel("Seitenanzahl:"));
        formPanel.add(txtSeiten);
        formPanel.add(new JLabel("Erscheinungsjahr:"));
        formPanel.add(txtJahr);
        formPanel.add(new JLabel("Preis (€):"));
        formPanel.add(txtPreis);
        formPanel.add(new JLabel("Sprache:"));
        formPanel.add(txtSprache);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(cbVerfuegbar);

        add(formPanel, BorderLayout.CENTER);

        // SOUTH: Buttons mit FlowLayout (Standard bei JPanel)
        JPanel buttonPanel = new JPanel(); 
        JButton btnSpeichern = new JButton("Speichern");
        JButton btnAbbrechen = new JButton("Abbrechen");

        buttonPanel.add(btnSpeichern);
        buttonPanel.add(btnAbbrechen);
        add(buttonPanel, BorderLayout.SOUTH);

        // EVENTS
        btnAbbrechen.addActionListener(e -> dispose());

        btnSpeichern.addActionListener(e -> {
            try {
                // Daten auslesen und umwandeln
                String titel = txtTitel.getText();
                String isbn = txtIsbn.getText();
                String autor = txtAutor.getText();
                int seiten = Integer.parseInt(txtSeiten.getText());
                int jahr = Integer.parseInt(txtJahr.getText());
                double preis = Double.parseDouble(txtPreis.getText());
                String sprache = txtSprache.getText();
                boolean verfuegbar = cbVerfuegbar.isSelected();

                // Neues Buch-Objekt erstellen
                Buch neuesBuch = new Buch(titel, isbn, autor, sprache, seiten, jahr, preis, verfuegbar);
                
                // Über Service speichern (MVC!)
                service.addBuch(neuesBuch);
                
                JOptionPane.showMessageDialog(this, "Buch erfolgreich gespeichert!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte prüfen Sie die Zahlenfelder (Seiten, Jahr, Preis).", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}