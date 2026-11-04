package lk.ac.mrt.fit.library_api.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Importing the JpaRepository interface from Spring Data JPA. Provides ready-made database operations.

import lk.ac.mrt.fit.library_api.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> { // This interface extends JpaRepository, allowing CRUD operations on Book entities. The first generic parameter is the entity type (Book), and the second is the type of the entity's primary key (Long).
}
