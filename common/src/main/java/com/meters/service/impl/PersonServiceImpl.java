package com.meters.service.impl;

import com.meters.dto.PersonDto;
import com.meters.entities.Person;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.PersonMapper;
import com.meters.repository.PersonRepository;
import com.meters.service.PersonService;
import lombok.RequiredArgsConstructor;
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
    public Optional<Person> createPerson(PersonDto personDto) {
        Person person = personMapper.toEntity(personDto);
        person.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(personRepository.save(person));
    }

    @Override
    public Optional<Person> updatePerson(Long id, PersonDto personDto) {
        Person person = findPerson(id);
        personMapper.updatePerson(personDto, person);
        return Optional.of(personRepository.save(person));
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(Long id) {
        Person person = findPerson(id);
        if(person.getIsDeleted().equals(Boolean.TRUE)) {
            throw new EntityIsDeletedException("Person is deleted");
        }
        return Optional.of(person);
    }

    @Override
    public Optional<Person> restoreDeletedPerson(Long id) {
        Person person = findPerson(id);
        person.setIsDeleted(false);
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(personRepository.save(person));
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person softDelete(Long id) {
        Person person = findPerson(id);
        person.setIsDeleted(true);
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return personRepository.save(person);
    }

    private Person findPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person could not be found"));
    }
}
