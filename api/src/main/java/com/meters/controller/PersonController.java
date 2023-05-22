package com.meters.controller;

import com.meters.requests.create.PersonRequest;
import com.meters.entities.Person;
import com.meters.requests.update.PersonUpdateRequest;
import com.meters.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @Value("${page-capacity.person}")
    private Integer pageCapacity;

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Person>> getAllPersonsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Person> persons = personService.findAll(pageable);

        if (persons.hasContent()) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personService.findById(id);
        if(person.isPresent()) {
            return new ResponseEntity<> (person.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody PersonRequest personRequest,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Person person = personService.createPerson(personRequest);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody PersonUpdateRequest personRequest, @PathVariable("id") Long id,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Person person = personService.updatePerson(id, personRequest);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") Long id) {
        personService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @GetMapping("/search/passport")
    public ResponseEntity<Person> getPersonByPassportNum(@RequestParam String passNum) {

        Optional<Person> person = personService.findByPassportNum(passNum);
        if(person.isPresent()) {
            return new ResponseEntity<> (person.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/fullname")
    public ResponseEntity<List<Person>> getPersonByFullName(@RequestParam String query) {
        List<Person> persons = personService.findByPersonFullNameContainingIgnoreCase(query);
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }
}
