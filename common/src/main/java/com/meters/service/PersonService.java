package com.meters.service;

import com.meters.entities.Person;
import com.meters.requests.create.PersonRequest;
import com.meters.requests.update.PersonUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person createPerson(PersonRequest personRequest);

    Person updatePerson(Long id, PersonUpdateRequest personRequest);

    List<Person> findAll();

    Page<Person> findAll(Pageable pageable);

    Optional<Person> findById(Long id);

    void deleteById(Long id);

    Optional<Person> findByPassportNum(String passNum);

    List<Person> findByPersonFullNameContainingIgnoreCase(String query);
}
