package org.example;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;
import de.maxi.buecherladen.actions.BookNewAction;
import de.maxi.buecherladen.actions.BookSearchAction;
import de.maxi.buecherladen.model.Book;
import de.maxi.buecherladen.model.BookManager;

/**
 * Einfache Hauptanwendung mit Registerkarten; nur Bücheransicht implementiert.
 * Der Navigationsbereich kann links ausgebaut werden, wie im Figma‑Design angedeutet.
 */
public class BookStoreApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookStoreApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Buchladen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Book‑Manager und UI‑Datenmodell
        BookManager bookManager = new BookManager();
        DefaultListModel<Book> bookListModel = new DefaultListModel<>();
        JList<Book> bookList = new JList<>(bookListModel);

        // Sucheingabe und Action für Suche
        PlainDocument searchDoc = new PlainDocument();
        BookSearchAction searchAction = new BookSearchAction(searchDoc, bookListModel, bookManager);
        JTextField searchField = new JTextField();
        searchField.setDocument(searchDoc);
        searchField.addActionListener(searchAction);

        // Panel für Bücher
        JPanel booksPanel = new JPanel(new BorderLayout());
        booksPanel.add(searchField, BorderLayout.NORTH);
        booksPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);
        // weitere Buttons (Add, Edit, Delete) kannst du hier hinzufügen

        // Registerkarten (Tabs)
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Bücher", booksPanel);
        // Analog: weiterer Tab "Mitarbeiter"
        frame.getContentPane().add(tabs);

        frame.setVisible(true);
    }
}
