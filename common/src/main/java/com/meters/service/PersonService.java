package com.meters.service;

import com.meters.dto.PersonDto;
import com.meters.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> createPerson(PersonDto personDto);

    Optional<Person> updatePerson(Long id, PersonDto personDto);

    List<Person> findAll();
    //List<Person> findAll(int page, int size);

    Optional<Person> findById(Long id);
    Optional<Person> restoreDeletedPerson(Long id);

    void deleteById(Long id);
    Person softDelete(Long id);
}
