package de.maxi.buecherladen.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
    import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import de.maxi.buecherladen.model.Book;
import de.maxi.buecherladen.model.BookManager;

/**
 * Sucht nach Büchern anhand eines Suchfeldes.
 */
public class BookSearchAction extends AbstractAction {

    private final Document searchInput;
    private final DefaultListModel<Book> searchResult;
    private final BookManager bookManager;

    public BookSearchAction(Document searchInput,
                            DefaultListModel<Book> searchResult,
                            BookManager bookManager) {
        super("Search");
        this.searchInput = searchInput;
        this.searchResult = searchResult;
        this.bookManager = bookManager;
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String query = searchInput.getText(0, searchInput.getLength()).trim();
            searchResult.clear();
            if (query.isEmpty()) {
                return;
            }
            List<Book> matched = bookManager.searchBooks(query);
            matched.forEach(searchResult::addElement);
        } catch (BadLocationException ex) {
            System.err.println("Fehler beim Auslesen des Suchfeldes: " + ex.getMessage());
        }
    }
}
