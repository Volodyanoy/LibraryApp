package org.example.volodyanoy.LibrarySpringBootApp.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty
    @Size(min = 2, max = 50, message = "Количество символов должно быть от 2 до 50")
    private String title;

    @Column(name = "author")
    @NotEmpty
    @Size(min = 2, max = 50, message = "Количество символов должно быть от 2 до 50")
    private String author;

    @Column(name = "year_of_writing")
    @NotNull
    @Min(value = 1700, message = "Год издания должен быть больше 1700")
    @Max(value = 2026, message = "Год издания должен быть меньше 2026")
    private Integer yearOfWriting;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "date_of_book_assignment")
    private LocalDateTime dateOfBookAssignment;

    @Transient
    boolean isOverdue;

    public Book() {
    }

    public Book(String title, String author, Integer yearOfWriting) {
        this.title = title;
        this.author = author;
        this.yearOfWriting = yearOfWriting;
    }

    public boolean isOverdue() {
        if (dateOfBookAssignment == null)
            return false;
        return ChronoUnit.DAYS.between(dateOfBookAssignment, LocalDateTime.now()) > 10;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateOfBookAssignment() {
        return dateOfBookAssignment;
    }

    public void setDateOfBookAssignment(LocalDateTime dateOfBookAssignment) {
        this.dateOfBookAssignment = dateOfBookAssignment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfWriting() {
        return yearOfWriting;
    }

    public void setYearOfWriting(Integer yearOfWriting) {
        this.yearOfWriting = yearOfWriting;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfWriting=" + yearOfWriting +
                '}';
    }
}
