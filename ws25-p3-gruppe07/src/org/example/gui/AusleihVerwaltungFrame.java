package org.example.gui;

import org.example.actions.AusleihSearchAction;
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
        String[] columns = {"ID", "Exemplar-ID", "Nutzer-ID"};
        model = new DefaultTableModel(columns, 0);
        ausleihTabelle = new JTable(model);
        // Sorter
        sorter = new TableRowSorter<>(model);
        ausleihTabelle.setRowSorter(sorter);

        datenInTabelleLaden();

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

        // 1. Such-Komponenten erstellen
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Suchen");

        // 2. Action initialisieren
        AusleihSearchAction searchAction = new AusleihSearchAction(txtSearch, model, service);

        // 3. Action zuweisen
        btnSearch.setAction(searchAction);
        txtSearch.addActionListener(searchAction);

        // 4. Panel oben hinzufügen
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(new JLabel("Suche (ID/Nutzer):"));
        northPanel.add(txtSearch);
        northPanel.add(btnSearch);

        add(northPanel, BorderLayout.NORTH);

        // Listener
        btnClose.addActionListener(e -> dispose());
        
        btnAdd.addActionListener(e -> {
            new AusleiheAnlegenDialog(this, service).setVisible(true);
            datenInTabelleLaden();
        });

        btnReturn.addActionListener(e -> {
            int row = ausleihTabelle.getSelectedRow();
            if (row != -1) {
                int modelRow = ausleihTabelle.convertRowIndexToModel(row);
                Ausleihe selektiert = service.getAlleAusleihen().get(modelRow);
                
                // Die neue Logik aufrufen
                service.ausleiheBeenden(selektiert);
                
                // GUI aktualisieren
                datenInTabelleLaden(); 
                JOptionPane.showMessageDialog(this, "Buch zurückgegeben. Es kann nun wieder ausgeliehen werden.");
            }
        });
    }

    // Tabelleninhalt
    private void datenInTabelleLaden() {
        model.setRowCount(0);
        for (Ausleihe a : service.getAlleAusleihen()) {
            model.addRow(new Object[]{
                a.getAusleihID(), a.getExemplarID(), a.getNutzerID()
            });
        }
    }
}