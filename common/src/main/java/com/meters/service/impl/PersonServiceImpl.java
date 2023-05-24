package com.meters.service.impl;

import com.meters.entities.Person;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.PersonMapper;
import com.meters.repository.PersonRepository;
import com.meters.requests.create.PersonRequest;
import com.meters.requests.update.PersonUpdateRequest;
import com.meters.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Override
    @Transactional
    public Person createPerson(PersonRequest personRequest) {
        Person person = personMapper.toEntity(personRequest);
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public Person updatePerson(Long id, PersonUpdateRequest personRequest) {
        Person person = findPerson(id);
        personMapper.updatePerson(personRequest, person);
        return personRepository.save(person);
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
        if (person.isDeleted()) {
            throw new EntityIsDeletedException("Person is deleted");
        }
        return Optional.of(person);
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
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
