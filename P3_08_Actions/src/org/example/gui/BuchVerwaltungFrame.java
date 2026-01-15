package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Buch;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;

public class BuchVerwaltungFrame extends JFrame{
   private BibliothekService service;
    private JTable buchTabelle;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;


    public BuchVerwaltungFrame(BibliothekService service) {
        this.service = service;

        setTitle("Bücher verwalten");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Nur dieses Fenster schließen
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // 1. Tabelle einrichten
        String[] spalten = {"Titel", "ISBN", "Autor", "Preis", "Verfügbar"};
        tableModel = new DefaultTableModel(spalten, 0);
        buchTabelle = new JTable(tableModel);
        
        // Sorter einrichten
        sorter = new TableRowSorter<>(tableModel);
        buchTabelle.setRowSorter(sorter);

        // Daten aus dem Service in das Tabellen-Model laden
        datenInTabelleLaden();

        JScrollPane scrollPane = new JScrollPane(buchTabelle);

        // ungenutzt
        add(scrollPane, BorderLayout.CENTER);

        // 2. Button-Leiste unten
        JPanel southPanel = new JPanel();
        JButton btnAdd = new JButton("Neues Buch");
        JButton btnDelete = new JButton("Löschen");
        JButton btnClose = new JButton("Zurück");

        southPanel.add(btnAdd);
        southPanel.add(btnDelete);
        southPanel.add(btnClose);
        // Exemplar
        JButton btnExemplarHinzufuegen = new JButton("Exemplar für Buch anlegen");
        btnExemplarHinzufuegen.addActionListener(e -> {
            new ExemplarAnlegenDialog(this, service).setVisible(true);
        });
        southPanel.add(btnExemplarHinzufuegen);

        add(southPanel, BorderLayout.SOUTH);
        
        // Suchleiste
        JPanel northPanel = new JPanel(new BorderLayout(5, 5)); // BorderLayout für saubere Anordnung
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Suchfeld
        JTextField suche = new JTextField(); 
        northPanel.add(new JLabel("Filtern: "), BorderLayout.WEST);
        northPanel.add(suche, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);

        // Suche-Echtzeitlistener
        suche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtern(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtern(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtern(); }

            private void filtern() {
                String text = suche.getText();
                if (text == null || text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // 3. Event-Handling
        btnClose.addActionListener(e -> dispose());

        // Buch hinzufügen
        btnAdd.addActionListener(e -> {
            BuchEingabeDialog dialog = new BuchEingabeDialog(this, service);
            dialog.setVisible(true);
            // Nach dem Schließen des Dialogs die Tabelle aktualisieren
            datenInTabelleLaden();
        });

        btnDelete.addActionListener(e -> {
            // 1. Prüfen, welche Zeile in der Tabelle ausgewählt ist
            int selectedRow = buchTabelle.getSelectedRow();
        
            if (selectedRow != -1) {
                // 2. Sicherheitsabfrage (Optional, aber sehr empfohlen für gute GUI-Noten)
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Möchten Sie dieses Buch wirklich löschen?", 
                    "Löschen bestätigen", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // 3. Das Buch-Objekt aus der Liste des Service holen
                    // Da die Tabelle die gleiche Reihenfolge wie die Liste hat:
                    Buch zuEntfernen = service.getAlleBuecher().get(selectedRow);
                
                        // 4. Den Controller (Service) anweisen, das Buch zu löschen
                        service.entferneBuch(zuEntfernen);
                        
                        // 5. Die View (Tabelle) aktualisieren, damit das Buch verschwindet
                        datenInTabelleLaden();
                        
                        JOptionPane.showMessageDialog(this, "Buch wurde erfolgreich gelöscht.");
                } else {
                        // Wenn nichts ausgewählt wurde
                        JOptionPane.showMessageDialog(this, "Bitte wählen Sie zuerst ein Buch in der Tabelle aus.");
                }
            }
        });
    }

    private void datenInTabelleLaden() {
        // Tabelle leeren
        tableModel.setRowCount(0);
        // Alle Bücher vom Service holen und einfügen
        for (Buch b : service.getAlleBuecher()) {
            Object[] row = {
                b.getTitel(), 
                b.getIsbn(), 
                b.getAutor(), 
                b.getPreis() + " €", 
            };
            tableModel.addRow(row);
        }
    }
}
