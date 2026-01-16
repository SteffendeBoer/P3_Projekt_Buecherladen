package org.example.actions;

import org.example.logic.BibliothekService;
import org.example.model.Ausleihe;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class AusleihSearchAction extends AbstractAction {
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private BibliothekService service;

    public AusleihSearchAction(JTextField searchField, DefaultTableModel tableModel, BibliothekService service) {
        super("Suchen");
        this.searchField = searchField;
        this.tableModel = tableModel;
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = searchField.getText();
        List<Ausleihe> result = service.searchAusleihen(query);
        
        tableModel.setRowCount(0); // Tabelle leeren
        
        for (Ausleihe a : result) {
            tableModel.addRow(new Object[]{
                a.getAusleihID(), 
                a.getExemplarID(), 
                a.getNutzerID()
            });
        }
    }
}