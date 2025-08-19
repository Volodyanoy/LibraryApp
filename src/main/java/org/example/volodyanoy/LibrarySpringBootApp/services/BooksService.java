package org.example.volodyanoy.LibrarySpringBootApp.services;

import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private static final Logger logger = LoggerFactory.getLogger(BooksService.class);


    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllAndSortByYearOfWriting(){
        return booksRepository.findAll(Sort.by("yearOfWriting"));
    }

    public List<Book> findAllAndPagination(Integer page, Integer booksPerPage){
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAllAndSortByYearOfWritingAndPagination(Integer page, Integer booksPerPage){
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfWriting"))).getContent();
    }

    public List<Book> findBooksByTitle(String searchQuery){
        return booksRepository.findByTitleStartingWith(searchQuery);

    }

    public Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Book findByTitleAndYearOfWritingAndAuthor(Book book){
        Optional<Book> foundBook = booksRepository.findByTitleAndYearOfWritingAndAuthor(book.getTitle(), book.getYearOfWriting(), book.getAuthor());
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
        logger.info("Успешно добавлена книга {}", book);

    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(int id){

        booksRepository.deleteById(id);
    }

    public Person getOwnerOfBook(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void releaseBook(int id){
        booksRepository.findById(id).ifPresent(book -> {
            Person owner = book.getOwner();
            if(owner != null)
                owner.removeBook(book);
        });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void assignBook(int id, Person person){
        booksRepository.findById(id).ifPresent(book -> {
            Person oldOwner = book.getOwner();
            if(oldOwner != null)
                oldOwner.removeBook(book);
            person.addBook(book);
        });
    }


}
