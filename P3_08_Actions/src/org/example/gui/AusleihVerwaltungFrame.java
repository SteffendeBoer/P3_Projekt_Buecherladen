package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Ausleihe;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;

public class AusleihVerwaltungFrame extends JFrame {
    private BibliothekService service;
    private JTable ausleihTabelle;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public AusleihVerwaltungFrame(BibliothekService service) {
        this.service = service;
        setTitle("Ausleihverwaltung");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Anforderung: BorderLayout

        // Tabelle initialisieren
        String[] columns = {"ID", "Exemplar-ID", "Nutzer-ID", "Datum", "Frist", "Gebühr"};
        model = new DefaultTableModel(columns, 0);
        ausleihTabelle = new JTable(model);
        // Sorter
        sorter = new TableRowSorter<>(model);
        ausleihTabelle.setRowSorter(sorter);

        datenLaden();

        add(new JScrollPane(ausleihTabelle), BorderLayout.CENTER);

        // Button-Leiste
        JPanel southPanel = new JPanel(); // Nutzt FlowLayout
        JButton btnAdd = new JButton("Neue Ausleihe");
        JButton btnReturn = new JButton("Rückgabe (Löschen)");
        JButton btnClose = new JButton("Schließen");

        southPanel.add(btnAdd);
        southPanel.add(btnReturn);
        southPanel.add(btnClose);
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

        // Listener
        btnClose.addActionListener(e -> dispose());
        
        btnAdd.addActionListener(e -> {
            new AusleiheAnlegenDialog(this, service).setVisible(true);
            datenLaden();
        });

        btnReturn.addActionListener(e -> {
            int row = ausleihTabelle.getSelectedRow();
            if (row != -1) {
                // 1. Das richtige Objekt aus der Liste holen
                Ausleihe selected = service.getAlleAusleihen().get(row);
                
                // 2. Den Service anweisen, genau dieses Objekt zu löschen
                service.entferneAusleihe(selected); 
                
                // 3. Anzeige aktualisieren
                datenLaden();
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Ausleihe aus.");
            }
        });
    }

    private void datenLaden() {
        model.setRowCount(0);
        for (Ausleihe a : service.getAlleAusleihen()) {
            model.addRow(new Object[]{
                a.getAusleihID(), a.getExemplarID(), a.getNutzerID(), 
                "Datum...", "Frist...", a.getAusleihID() // IDs und Werte
            });
        }
    }
}