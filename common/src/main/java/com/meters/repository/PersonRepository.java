package com.meters.repository;

import com.meters.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByPassportNum(String passNum);

    List<Person> findByPersonFullNameContainingIgnoreCase(String query);
}