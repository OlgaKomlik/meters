package com.meters.service.impl;

import com.meters.requests.PersonRequest;
import com.meters.entities.Person;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.PersonMapper;
import com.meters.repository.PersonRepository;
import com.meters.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {


    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public Optional<Person> createPerson(PersonRequest personRequest) {
        Person person = personMapper.toEntity(personRequest);
        person.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(personRepository.save(person));
    }

    @Override
    public Optional<Person> updatePerson(Long id, PersonRequest personRequest) {
        Person person = findPerson(id);
        personMapper.updatePerson(personRequest, person);
        return Optional.of(personRepository.save(person));
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Optional<Person> findById(Long id) {
        Person person = findPerson(id);
        if(person.isDeleted()) {
            throw new EntityIsDeletedException("Person is deleted");
        }
        return Optional.of(person);
    }

    @Override
    public Optional<Person> restoreDeletedPerson(Long id) {
        Person person = findPerson(id);
        person.setDeleted(false);
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(personRepository.save(person));
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person deactivate(Long id) {
        Person person = findPerson(id);
        person.setDeleted(true);
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return personRepository.save(person);
    }

    private Person findPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person could not be found"));
    }

    public Optional<Person> findByPassportNum(String passNum) {
        return personRepository.findByPassportNum(passNum);
    }

    public List<Person> findByPersonFullNameContainingIgnoreCase(String query) {
        return personRepository.findByPersonFullNameContainingIgnoreCase(query);
    }
}
