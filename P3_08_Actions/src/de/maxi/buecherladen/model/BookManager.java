package de.maxi.buecherladen.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
    import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verwaltet eine Liste von Büchern und ermöglicht Suche, Hinzufügen, Entfernen sowie Laden/Speichern.
 */
public class BookManager {
    private final List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book addBook(String title, String author, String isbn,
                        double price, String genre, String language, int quantity) {
        Book book = new Book(nextId++, title, author, isbn, price, genre, language, quantity);
        books.add(book);
        return book;
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void updateBook(Book updated) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == updated.getId()) {
                books.set(i, updated);
                break;
            }
        }
    }

    /**
     * Sucht Bücher, deren Titel oder Autor den Suchbegriff enthalten (case‑insensitive).
     */
    public List<Book> searchBooks(String query) {
        String q = query.toLowerCase();
        return books.stream()
                    .filter(b -> b.getTitle().toLowerCase().contains(q)
                              || b.getAuthor().toLowerCase().contains(q))
                    .collect(Collectors.toList());
    }

    /**
     * Speichert alle Bücher in eine CSV‑Datei (id;titel;author;isbn;price;genre;language;quantity).
     */
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book b : books) {
                bw.write(String.format("%d;%s;%s;%s;%f;%s;%s;%d%n",
                    b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(),
                    b.getPrice(), b.getGenre(), b.getLanguage(), b.getQuantity()));
            }
        }
    }

    /**
     * Lädt Bücher aus einer CSV‑Datei; vorhandene Liste wird überschrieben.
     */
    public void loadFromFile(String filename) throws IOException {
        books.clear();
        nextId = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length >= 8) {
                    int id        = Integer.parseInt(parts[0]);
                    String title  = parts[1];
                    String author = parts[2];
                    String isbn   = parts[3];
                    double price  = Double.parseDouble(parts[4]);
                    String genre  = parts[5];
                    String lang   = parts[6];
                    int qty       = Integer.parseInt(parts[7]);
                    books.add(new Book(id, title, author, isbn, price, genre, lang, qty));
                    nextId = Math.max(nextId, id + 1);
                }
            }
        }
    }
}
