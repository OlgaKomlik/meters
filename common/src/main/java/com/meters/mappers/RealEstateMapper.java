package com.meters.mappers;

import com.meters.dto.RealEstateDto;
import com.meters.entities.RealEstate;
import com.meters.entities.enums.ClientType;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.CompanyRepository;
import com.meters.repository.LocationRepository;
import com.meters.repository.PersonRepository;
import com.meters.repository.catalogs.ObjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RealEstateMapper {

    private final ModelMapper modelMapper;
    private final LocationRepository locationRepository;
    private final ObjectTypeRepository objectTypeRepository;
    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;

    public RealEstate toEntity(RealEstateDto realEstateDto) {

        RealEstate realEstate = modelMapper.map(realEstateDto, RealEstate.class);
        setLocation(realEstateDto, realEstate);
        setObjectType(realEstateDto, realEstate);
        chooseOwnerByType(realEstateDto, realEstate);
        return realEstate;
    }

    public RealEstateDto toDto(RealEstate realEstate) {
        RealEstateDto realEstateDto = modelMapper.map(realEstate, RealEstateDto.class);
        realEstateDto.setLocation(realEstate.getLocation().getId());
        realEstateDto.setObjectType(realEstate.getObjectType().getId());
        if(realEstate.getOwnerCompany() != null) {
        realEstateDto.setOwner(realEstate.getOwnerCompany().getId());
        } else if (realEstate.getOwnerPerson() != null) {
            realEstateDto.setOwner(realEstate.getOwnerPerson().getId());
        }
        return realEstateDto;
    }

    public RealEstate updateRealEstate(RealEstateDto realEstateDto, RealEstate realEstate) {
        if(realEstateDto.getSquare() != null) {
            realEstate.setSquare(realEstateDto.getSquare());
        }
        if(realEstateDto.getRooms() != null) {
            realEstate.setRooms(realEstateDto.getRooms());
        }
        if(realEstateDto.getFloors() != null) {
            realEstate.setFloors(realEstateDto.getFloors());
        }
        if(realEstateDto.getGardenSquare() != null) {
            realEstate.setGardenSquare(realEstateDto.getGardenSquare());
        }
        if(realEstateDto.getGarage() != null) {
            realEstate.setGarage(realEstateDto.getGarage());
        }
        if(realEstateDto.getAddress() != null) {
            realEstate.setAddress(realEstateDto.getAddress());
        }
        if(realEstateDto.getOwnerClientType() != null) {
            realEstate.setOwnerClientType(ClientType.valueOf(realEstateDto.getOwnerClientType()));
        }
        if(realEstateDto.getOwner() != null) {
            chooseOwnerByType(realEstateDto, realEstate);
        }
        if(realEstateDto.getLocation() != null) {
            setLocation(realEstateDto, realEstate);
        }
        if(realEstateDto.getObjectType() != null) {
            setObjectType(realEstateDto, realEstate);
        }



        realEstate.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return realEstate;
    }

    public void chooseOwnerByType (RealEstateDto realEstateDto, RealEstate realEstate) {
        if(realEstateDto.getOwnerClientType().equals(ClientType.COMPANY.toString())) {
            realEstate.setOwnerCompany(companyRepository.findById(realEstateDto.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Company not exist")));
        } else if (realEstateDto.getOwnerClientType().equals(ClientType.PERSON.toString())) {
            realEstate.setOwnerPerson(personRepository.findById(realEstateDto.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Person not exist")));
        }
    }

    public void setLocation(RealEstateDto realEstateDto, RealEstate realEstate) {
        realEstate.setLocation(locationRepository.findById(realEstateDto.getLocation())
                .orElseThrow(() -> new EntityNotFoundException("Location not exist")));
    }

    public void setObjectType(RealEstateDto realEstateDto, RealEstate realEstate) {
        realEstate.setObjectType(objectTypeRepository.findById(realEstateDto.getObjectType())
                .orElseThrow(() -> new EntityNotFoundException("ObjectType not exist")));
    }
}
