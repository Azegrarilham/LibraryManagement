package service;

import java.util.ArrayList;

import model.Book;
import util.FileManager;

public class BookService {

    private ArrayList<Book> books;

    public BookService() {
        books = FileManager.load("books.dat");
    }

    // Add a book
    public boolean addBook(Book book) {
        if (findBookByCode(book.getCode()) != null) {
            return false;
        }

        books.add(book);
        FileManager.save("books.dat", books);
        return true;
    }

    // Return all books
    public ArrayList<Book> getAllBooks() {
        return books;
    }

    // Search by code
    public Book findBookByCode(String code) {
        for (Book book : books) {
            if (book.getCode().equalsIgnoreCase(code)) {
                return book;
            }
        }
        return null;
    }

    // Search by title
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    // Search by author
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    // Update a book
    public boolean updateBook(Book updatedBook) {
        Book book = findBookByCode(updatedBook.getCode());

        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setCategory(updatedBook.getCategory());
            book.setPublicationYear(updatedBook.getPublicationYear());
            book.setAvailableCopies(updatedBook.getAvailableCopies());
            FileManager.save("books.dat", books);
            return true;
        }

        return false;
    }

    // Delete a book
    public boolean deleteBook(String code) {
        Book book = findBookByCode(code);

        if (book != null) {
            books.remove(book);
            FileManager.save("books.dat", books);
            return true;
        }

        return false;
    }

    public void saveBooks() {
        FileManager.save("books.dat", books);
    }
}
