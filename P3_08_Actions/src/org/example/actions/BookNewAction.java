package org.example.actions;

import org.example.model.BuchManager;
import org.example.model.Buch;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class BookNewAction extends AbstractAction { // Klasse umbenennen

    private BookManager bookService;

    public BookNewAction(String text, ImageIcon icon, String desc, Integer mnemonic, BookManager bookService) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.bookService = bookService;
    }
            
    @Override
    public void actionPerformed(ActionEvent e) {
        // Eingabe Titel
        String title = JOptionPane.showInputDialog(null, "Bitte geben sie den Buchtitel an: ");

        if (title == null || title.trim().isEmpty()) {
            // Eingabe abbrechen
            return;
        }
        // Eingabe ISBN
        String isbn = JOptionPane.showInputDialog(null, "Bitte geben sie die ISBN an: ");

        if (isbn == null || isbn.trim().isEmpty()) {
            return;
        }

        Book newboob = new Book(isbn.trim(), title.trim());

        bookService.addBook(newboob);

        JOptionPane.showMessageDialog(null, "Buch '" + title + "' gespeichert");

        

        // Text anpassen
        JOptionPane.showMessageDialog(null, "Would have done the 'Create new Book' action.");
    }
}
