package lk.ac.mrt.fit.library_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lk.ac.mrt.fit.library_api.model.Book;
import lk.ac.mrt.fit.library_api.service.BookService;


@Tag(
    name = "Books",
    description = "Create, read, update and delete books"
)
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}") // This method handles GET requests to the /api/books/{id} endpoint. It retrieves a book by its ID using the BookService. If the book is found, it returns a ResponseEntity with the book and an HTTP status of 200 OK. If not found, it returns a ResponseEntity with an HTTP status of 404 Not Found.
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {  // The @PathVariable annotation indicates that the id parameter should be extracted from the URL path. For example, if the request is made to /api/books/1, the id parameter will be set to 1.
        return bookService.getBookById(id)
            .map(ResponseEntity::ok)  // If the book is found, the map function transforms the Optional<Book> into a ResponseEntity<Book> with an HTTP status of 200 OK.
            .orElse(ResponseEntity.notFound().build());  // If the book is not found, the orElse function returns a ResponseEntity with an HTTP status of 404 Not Found.
    }



    @Operation(
    summary = "Create a book",
    description = "Adds a book and returns it with its new id."
)
    @ApiResponse(
    responseCode = "201",
    description = "Book created"
)
    @ApiResponse(
    responseCode = "400",
    description = "Invalid input"
)
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {  //@RequestBody - This tells Spring to read the incoming JSON request body and convert it into a Java Book object. @Valid - This annotation is used to trigger validation on the Book object based on the constraints defined in the Book class (e.g., @NotBlank, @PositiveOrZero). If the validation fails, Spring will automatically return a 400 Bad Request response with details about the validation errors.
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book) {

        return bookService.updateBook(id, book)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
