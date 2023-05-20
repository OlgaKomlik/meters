package com.meters.service;

import com.meters.requests.PersonRequest;
import com.meters.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> createPerson(PersonRequest personRequest);

    Optional<Person> updatePerson(Long id, PersonRequest personRequest);

    List<Person> findAll();
    Page<Person> findAll(Pageable pageable);

    Optional<Person> findById(Long id);
    Optional<Person> activatePerson(Long id);

    void deleteById(Long id);
    Person deactivate(Long id);

    Optional<Person> findByPassportNum(String passNum);
    List<Person> findByPersonFullNameContainingIgnoreCase(String query);
}
