package org.example.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.example.model.Buch;
import org.example.logic.BibliothekService;

public class BuchSearchAction extends AbstractAction {
    private JTextField searchInput;
    private DefaultTableModel tableModel;
    private BibliothekService buchService;

    public BuchSearchAction(JTextField searchInput, DefaultTableModel tableModel, BibliothekService service) {
        super("Suchen");
        this.searchInput = searchInput;
        this.tableModel = tableModel;
        this.buchService = service;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = searchInput.getText();
        List<Buch> matchedBuchs = buchService.searchBuchs(query);
        
        // Tabelle leeren
        tableModel.setRowCount(0);
        
        // Gefilterte Ergebnisse einfügen
        for (Buch b : matchedBuchs) {
            tableModel.addRow(new Object[]{
                b.getTitel(), 
                b.getIsbn(), 
                b.getAutor(), 
                b.getSprache(),         // 3
                b.getSeitenzahl(),      // 4
                b.getErscheinungsjahr(),// 5
                b.getPreis() + " €",    // 6
                b.IstVerfuegbar()        // 7 (Die Checkbox)
            });
        }
    }
}