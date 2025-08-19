package org.example.volodyanoy.LibrarySpringBootApp.repositories;

import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWith(String startingWith);

    Optional<Book> findByTitleAndYearOfWritingAndAuthor(String title, int yearOfWriting, String author);

}
