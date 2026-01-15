package org.example.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.example.model.Buch;
import org.example.model.BuchManager;

public class BuchSearchAction extends AbstractAction { // Klasse umbenennen
    
    private Document searchInput;
    private DefaultListModel searchResult;
    private BuchManager BuchService; // Typ und Name anpassen

    // Konstruktor-Parameter anpassen
    public BuchSearchAction(Document searchInput, DefaultListModel searchResult, BuchManager BuchService){ 
        super("Search");
        this.searchInput = searchInput;
        this.searchResult = searchResult;
        this.buchService = BuchService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Methode und Typ anpassen
            List<Buch> matchedBuchs = buchService.searchBuchs(searchInput.getText(0, searchInput.getLength()));
            
            searchResult.clear();
            for (Buch buch : matchedBuchs) { // Schleifenvariable und Typ anpassen
                Object elementModel = buch;
                searchResult.addElement(elementModel);
            }
        } catch (BadLocationException ex) {
            System.err.println("BadLocationException: " + ex.getMessage());
        }
    }
}