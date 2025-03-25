package com.ayush.bookstore.service;

import com.ayush.bookstore.models.Book;
import com.ayush.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ayush.bookstore.exception.BookNotFoundException;

import java.util.List;

@Service // Marks this class as a service component
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Adds a new book to the database
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Retrieves all books from the database
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Retrieves a book by its ID, throws an exception if not found
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));
    }

    // Updates an existing book, throws an exception if the book does not exist
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    book.setPublishedDate(updatedBook.getPublishedDate());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));
    }

    // Deletes a book by its ID, throws an exception if not found
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }
}
