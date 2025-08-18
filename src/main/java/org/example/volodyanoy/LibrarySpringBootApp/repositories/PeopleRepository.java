package org.example.volodyanoy.LibrarySpringBootApp.repositories;

import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.books LEFT JOIN FETCH p.account")
    List<Person> findAllWithBooksAndAccounts();


    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.account")
    List<Person> findAllWithAccounts();


}
