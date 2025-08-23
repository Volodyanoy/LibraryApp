package org.example.volodyanoy.LibrarySpringBootApp.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Account account;

    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Количество символов должно быть от 2 до 50")
    private String name;

    @Column(name = "year_of_birth")
    @NotNull(message = "Год рождения не должен быть пустым")
    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    @Max(value = 2026, message = "Год рождения должен быть меньше 2026")
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Person() {
    }

    public Person(String name, Integer yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "yearOfBirth=" + yearOfBirth +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public void addBook(Book book) {
        book.setDateOfBookAssignment(LocalDateTime.now());
        book.setOwner(this);
        books.add(book);
    }

    public void removeBook(Book book) {
        book.setDateOfBookAssignment(null);
        book.setOwner(null);
        books.remove(book);
    }
}
