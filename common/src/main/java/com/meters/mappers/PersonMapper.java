package com.meters.mappers;

import com.meters.entities.Person;
import com.meters.requests.create.PersonRequest;
import com.meters.requests.update.PersonUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class PersonMapper {
    private final ModelMapper modelMapper;

    public Person toEntity(PersonRequest personRequest) {
        Person person = modelMapper.map(personRequest, Person.class);
        person.setPersonFullName(personRequest.getPersonName() + " " + personRequest.getSurname());
        return person;
    }

    public Person updatePerson(PersonUpdateRequest personRequest, Person person) {
        if (personRequest.getPersonName() != null) {
            person.setPersonName(personRequest.getPersonName());
        }

        if (personRequest.getSurname() != null) {
            person.setSurname(personRequest.getSurname());
        }

        if (personRequest.getBirthDate() != null) {
            person.setBirthDate(personRequest.getBirthDate());
        }

        if (personRequest.getPhoneNum() != null) {
            person.setPhoneNum(personRequest.getPhoneNum());
        }

        if (personRequest.getPassportNum() != null) {
            person.setPassportNum(personRequest.getPassportNum());
        }

        if (personRequest.getIsDeleted() != null) {
            person.setDeleted(personRequest.getIsDeleted());
        }
        person.setPersonFullName(person.getPersonName() + " " + person.getSurname());
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return person;
    }
}
