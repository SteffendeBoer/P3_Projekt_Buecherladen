package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Nutzer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;

public class NutzerVerwaltungFrame extends JFrame {
    private BibliothekService service;
    private JTable nutzerTabelle;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public NutzerVerwaltungFrame(BibliothekService service) {
        this.service = service;

        setTitle("Nutzerverwaltung");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Tabelle
        String[] spalten = {"ID", "Vorname", "Nachname", "E-Mail"};
        tableModel = new DefaultTableModel(spalten, 0);
        nutzerTabelle = new JTable(tableModel);

        // Sorter kopiert
        sorter = new TableRowSorter<>(tableModel);
        nutzerTabelle.setRowSorter(sorter);

        datenInTabelleLaden();

        add(new JScrollPane(nutzerTabelle), BorderLayout.CENTER);

        // Buttons
        JPanel southPanel = new JPanel(); // Nutzt FlowLayout
        JButton btnAdd = new JButton("Neuer Nutzer");
        JButton btnDelete = new JButton("Löschen");
        JButton btnClose = new JButton("Zurück");

        southPanel.add(btnAdd);
        southPanel.add(btnDelete);
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

        // Events
        btnClose.addActionListener(e -> dispose());

        btnAdd.addActionListener(e -> {
            new NutzerEingabeDialog(this, service).setVisible(true);
            datenInTabelleLaden();
        });

        btnDelete.addActionListener(e -> {
            int row = nutzerTabelle.getSelectedRow();
            if (row != -1) {
                Nutzer n = service.getAlleNutzer().get(row);
                service.entferneNutzer(n);
                datenInTabelleLaden();
            }
        });
    }

    private void datenInTabelleLaden() {
        tableModel.setRowCount(0);
        for (Nutzer n : service.getAlleNutzer()) {
            // Hinweis: stelle sicher, dass deine Nutzer-Klasse diese Getter hat!
            tableModel.addRow(new Object[]{n.getNutzerID(), n.getVorname(), n.getNachname(), "N/A"});
        }
    }
}