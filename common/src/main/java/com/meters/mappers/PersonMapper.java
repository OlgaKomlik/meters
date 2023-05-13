package com.meters.mappers;

import com.meters.dto.PersonDto;
import com.meters.entities.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class PersonMapper {
    private final ModelMapper modelMapper;

    public Person toEntity(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    public PersonDto toDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }
    public Person updatePerson(PersonDto personDto, Person person) {
        if(personDto.getPersonName() != null) {
            person.setPersonName(personDto.getPersonName());
        }

        if(personDto.getSurname() != null) {
            person.setSurname(personDto.getSurname());
        }

        if(personDto.getPersonFullName() != null) {
            person.setPersonFullName(personDto.getPersonFullName());
        }

        if(personDto.getBirthDate() != null) {
            person.setBirthDate(personDto.getBirthDate());
        }

        if(personDto.getPhoneNum() != null) {
            person.setPhoneNum(personDto.getPhoneNum());
        }

        if(personDto.getPassportNum() != null) {
            person.setPassportNum(personDto.getPassportNum());
        }
        person.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        return person;
    }
}
