package org.example.actions;

import org.example.logic.BibliothekService;
import org.example.model.Nutzer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class NutzerSearchAction extends AbstractAction {
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private BibliothekService service;

    public NutzerSearchAction(JTextField searchField, DefaultTableModel tableModel, BibliothekService service) {
        super("Suchen"); // Name des Buttons/Aktion
        this.searchField = searchField;
        this.tableModel = tableModel;
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = searchField.getText();
        
        // 1. Service nach gefilterter Liste fragen
        List<Nutzer> result = service.searchNutzer(query);
        
        // 2. Tabellenmodell leeren
        tableModel.setRowCount(0);
        
        // 3. Nur die Treffer in die Tabelle schreiben
        for (Nutzer n : result) {
            tableModel.addRow(new Object[]{
                n.getNutzerID(), 
                n.getVorname(), 
                n.getNachname(), 
                n.getEmail()
            });
        }
    }
}