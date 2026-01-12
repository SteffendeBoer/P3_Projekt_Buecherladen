package de.maxi.buecherladen.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import de.maxi.buecherladen.model.Book;
import de.maxi.buecherladen.model.BookManager;

/**
 * Fügt über ein UI‑Formular ein neues Buch hinzu.
 */
public class BookNewAction extends AbstractAction {

    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField isbnField;
    private final JTextField priceField; 
    private final JTextField genreField;
    private final JTextField languageField;
    private final JTextField quantityField;

    private final DefaultListModel<Book> bookListModel;
    private final BookManager bookManager;

    public BookNewAction(JTextField titleField,
                         JTextField authorField,
                         JTextField isbnField,
                         JTextField priceField,
                         JTextField genreField,
                         JTextField languageField,
                         JTextField quantityField,
                         DefaultListModel<Book> bookListModel,
                         BookManager bookManager) {
        super("Add Book");
        this.titleField     = titleField;
        this.authorField    = authorField;
        this.isbnField      = isbnField;
        this.priceField     = priceField;
        this.genreField     = genreField;
        this.languageField  = languageField;
        this.quantityField  = quantityField;
        this.bookListModel  = bookListModel;
        this.bookManager    = bookManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String title    = titleField.getText().trim();
        String author   = authorField.getText().trim();
        String isbn     = isbnField.getText().trim();
        String priceStr = priceField.getText().trim();
        String genre    = genreField.getText().trim();
        String lang     = languageField.getText().trim();
        String qtyStr   = quantityField.getText().trim();

        if (title.isEmpty() || author.isEmpty()) {
            // optional: Feedbackdialog anzeigen
            return;
        }

        double price  = priceStr.isEmpty() ? 0.0 : Double.parseDouble(priceStr);
        int quantity = qtyStr.isEmpty() ? 0 : Integer.parseInt(qtyStr);

        Book book = bookManager.addBook(title, author, isbn, price, genre, lang, quantity);
        bookListModel.addElement(book);

        // Eingabefelder leeren
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        priceField.setText("");
        genreField.setText("");
        languageField.setText("");
        quantityField.setText("");
    }
}
