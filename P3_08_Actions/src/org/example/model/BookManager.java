package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BookManager {
    
    private List<Book> books;
    
    public BookManager(){
        books = new ArrayList<>();
        books.add(new Book(12345, "Steffen"));
        books.add(new Book(23451, "Maxi"));
    
    }
    
    public List<Book> searchBooks(String searchString) {
        List<Book> matches = new ArrayList<>();
        
        if (searchString == null) {
            return matches;
        }
        
        for (Book b : books){
            if (b.getName().contains(searchString))
                matches.add(b);
        }
        return matches;
    }
    
    
    
}
