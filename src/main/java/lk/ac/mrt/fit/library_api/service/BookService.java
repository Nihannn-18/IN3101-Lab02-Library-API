package lk.ac.mrt.fit.library_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lk.ac.mrt.fit.library_api.model.Book;
import lk.ac.mrt.fit.library_api.repository.BookRepository;

@Service // Indicates that this class is a service component in the Spring context. It is responsible for business logic related to Book entities.
public class BookService {

    private final BookRepository bookRepository;  // Constructor Dependency injection of the BookRepository. This allows the service to perform database operations on Book entities.

    public BookService(BookRepository bookRepository) { // Constructor for the BookService class. It takes a BookRepository as a parameter and assigns it to the bookRepository field. Doesn't use @Autowired because Spring automatically injects the dependency through the constructor when class has only one constructor.
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() { // Method to retrieve all Book entities from the database. It calls the findAll() method of the BookRepository, which returns a list of all books stored in the database.
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) { // Method to retrieve a specific Book entity by its ID. It calls the findById() method of the BookRepository, which returns an Optional containing the book if found, or an empty Optional if not found.   
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book details) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(details.getTitle());
            book.setAuthor(details.getAuthor());
            book.setIsbn(details.getIsbn());
            book.setPrice(details.getPrice());
            return bookRepository.save(book);
        });
    }

    public boolean deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return false;
        }

        bookRepository.deleteById(id);
        return true;
    }
}
