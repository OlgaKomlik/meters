package com.meters.controller;

import com.meters.dto.PersonDto;
import com.meters.entities.Person;
import com.meters.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<Object> getAllPersons() {
        List<Person> persons = personService.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Person>> createPerson(@Valid @RequestBody PersonDto personDto) {
        Optional<Person> person = personService.createPerson(personDto);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<Person>> updatePerson(@Valid @RequestBody PersonDto personDto, @PathVariable("id") Long id) {
        Optional<Person> person = personService.updatePerson(id, personDto);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> softDeletePerson(@PathVariable("id") Long id) {
        personService.softDelete(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletePerson(@PathVariable("id") Long id) {
        personService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Person>> restoreDeletedPerson(@PathVariable("id") Long id) {
        Optional<Person> person = personService.restoreDeletedPerson(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}
