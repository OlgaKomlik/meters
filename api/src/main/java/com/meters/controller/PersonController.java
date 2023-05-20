package com.meters.controller;

import com.meters.requests.PersonRequest;
import com.meters.entities.Person;
import com.meters.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @Value("${person.page-capacity}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<Object> getAllPersons() {
        List<Person> persons = personService.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllPersonsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Person> persons = personService.findAll(pageable);

        if (persons.hasContent()) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Optional<Person>> createPerson(@Valid @RequestBody PersonRequest personRequest) {
        Optional<Person> person = personService.createPerson(personRequest);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Person>> updatePerson(@Valid @RequestBody PersonRequest personRequest, @PathVariable("id") Long id) {
        Optional<Person> person = personService.updatePerson(id, personRequest);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivatePerson(@PathVariable("id") Long id) {
        personService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") Long id) {
        personService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<Person>> activatePerson(@PathVariable("id") Long id) {
        Optional<Person> person = personService.activatePerson(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/pass_num")
    public ResponseEntity<Optional<Person>> getPersonByPassportNum(@RequestParam String passNum) {
        return new ResponseEntity<>(personService.findByPassportNum(passNum), HttpStatus.OK);
    }

    @GetMapping("/full_name")
    public ResponseEntity<List<Person>> getPersonByFullName(@RequestParam String query) {
        return new ResponseEntity<>(personService.findByPersonFullNameContainingIgnoreCase(query), HttpStatus.OK);
    }
}
