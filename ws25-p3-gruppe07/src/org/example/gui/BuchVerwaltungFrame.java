package org.example.gui;

import org.example.actions.BuchSearchAction;
import org.example.logic.BibliothekService;
import org.example.model.Buch;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
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
        String[] spalten = {"Titel", "ISBN", "Autor", "Sprache", "Seitenanzahl", "Erscheinungsjahr", "Preis", "Verfügbar"};
        tableModel = new DefaultTableModel(spalten, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Die Spalte 7 (Verfügbar) ist Boolean -> Checkbox
                if (columnIndex == 7) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Nur die Spalte 7 soll editierbar sein
                return column == 7;
            }
        };        
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
        JButton btnExemplarHinzufuegen = new JButton("Exemplar anlegen");

        southPanel.add(btnAdd);
        southPanel.add(btnExemplarHinzufuegen); // Hinzufügen zum Panel
        southPanel.add(btnDelete);
        southPanel.add(btnClose);

        // In BuchVerwaltungFrame.java im Konstruktor
        btnExemplarHinzufuegen.addActionListener(e -> {
            int selectedRow = buchTabelle.getSelectedRow();

            // Sorter berücksichtigen
            int modelRow = buchTabelle.convertRowIndexToModel(selectedRow);
            Buch ausgewähltesBuch = service.getAlleBuecher().get(modelRow);

            if (ausgewähltesBuch.IstVerfuegbar()) {
                // Dialog öffnen
                ExemplarAnlegenDialog dialog = new ExemplarAnlegenDialog(service);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Dieses Buch ist inaktiv. Aktivieren Sie es erst, um Exemplare anzulegen.", 
                    "Hinweis", JOptionPane.WARNING_MESSAGE);
            }
        });
        southPanel.add(btnExemplarHinzufuegen);

        add(southPanel, BorderLayout.SOUTH);
        
        // Such-Komponenten 
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Suchen");

        //  initialisieren
        BuchSearchAction searchAction = new BuchSearchAction(txtSearch, tableModel, service);

        // Action Komponenten zuweisen
        btnSearch.setAction(searchAction);
        txtSearch.addActionListener(searchAction); // Suche bei ENTER im Textfeld

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        northPanel.add(new JLabel("Bücher suchen: "));
        northPanel.add(txtSearch);
        northPanel.add(btnSearch);

        add(northPanel, BorderLayout.NORTH);

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

        // Listener für die Checkbox-Klicks
        tableModel.addTableModelListener(e -> {
            // Prüfen, ob eine Zelle aktualisiert wurde und ob es die Checkbox-Spalte (7) ist
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 7) {
                int row = e.getFirstRow();
                
                // Den neuen Wert (true/false) aus der Tabelle holen
                Boolean neuerStatus = (Boolean) tableModel.getValueAt(row, 7);
                
                // Das entsprechende Buch-Objekt aus dem Service holen
                // Wichtig: Falls du einen Sorter nutzt, verwende buchTabelle.convertRowIndexToModel(row)
                int modelRow = buchTabelle.convertRowIndexToModel(row);
                Buch ausgewähltesBuch = service.getAlleBuecher().get(modelRow);
                
                // Status im Objekt setzen
                ausgewähltesBuch.setIstVerfuegbar(neuerStatus);
                
                // Speichern auslösen
                service.speichernAlleDaten();
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
                b.getSprache(),
                b.getSeitenzahl(),
                b.getErscheinungsjahr(),
                b.getPreis() + " €",
                b.IstVerfuegbar()
            };
            tableModel.addRow(row);
        }
    }
}
