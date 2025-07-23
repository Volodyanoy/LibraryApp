package org.example.volodyanoy.dao;

import org.example.volodyanoy.models.Person;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private  final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Optional<Person> show(String name, int yearOfBirth){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name = ? AND yearOfBirth = ?", new Object[]{name, yearOfBirth},
                new PersonMapper()).stream().findAny();
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{id},
                        new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, yearOfBirth) VALUES(?, ?)", person.getName(),
                person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, yearOfBirth=? WHERE person_id=?",
                updatedPerson.getName(), updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
    // input id = book_id
    public Person showOwnerOfBook(int id){
        return jdbcTemplate.query("SELECT person.person_id, name, yearofbirth\n" +
                "FROM person inner join book on person.person_id = book.person_id\n" +
                "WHERE book.book_id = ?", new Object[]{id}, new PersonMapper()).stream().findAny().orElse(null);
    }



}