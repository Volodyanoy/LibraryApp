package org.example.volodyanoy.LibrarySpringBootApp.repositories;

import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Person> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);
}
