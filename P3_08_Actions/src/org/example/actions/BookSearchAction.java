package org.example.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.example.model.Book;
import org.example.model.BookManager;

public class BookSearchAction extends AbstractAction { // Klasse umbenennen
    
    private Document searchInput;
    private DefaultListModel searchResult;
    private BookManager bookService; // Typ und Name anpassen

    // Konstruktor-Parameter anpassen
    public BookSearchAction(Document searchInput, DefaultListModel searchResult, BookManager bookService){ 
        super("Search");
        this.searchInput = searchInput;
        this.searchResult = searchResult;
        this.bookService = bookService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Methode und Typ anpassen
            List<Book> matchedBooks = bookService.searchBooks(searchInput.getText(0, searchInput.getLength()));
            
            searchResult.clear();
            for (Book book : matchedBooks) { // Schleifenvariable und Typ anpassen
                Object elementModel = book;
                searchResult.addElement(elementModel);
            }
        } catch (BadLocationException ex) {
            System.err.println("BadLocationException: " + ex.getMessage());
        }
    }
}