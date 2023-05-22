package com.meters.mappers;

import com.meters.requests.create.RealEstateRequest;
import com.meters.entities.RealEstate;
import com.meters.entities.constants.ClientType;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.CompanyRepository;
import com.meters.repository.LocationRepository;
import com.meters.repository.PersonRepository;
import com.meters.repository.catalogs.ObjectTypeRepository;
import com.meters.requests.update.RealEstateUpdateRequest;
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

    public RealEstate toEntity(RealEstateRequest realEstateRequest) {

        RealEstate realEstate = modelMapper.map(realEstateRequest, RealEstate.class);
        setLocation(realEstateRequest, realEstate);
        setObjectType(realEstateRequest, realEstate);
        chooseOwnerByType(realEstateRequest, realEstate);
        return realEstate;
    }

    public RealEstate updateRealEstate(RealEstateUpdateRequest realEstateRequest, RealEstate realEstate) {
        if(realEstateRequest.getSquare() != null) {
            realEstate.setSquare(realEstateRequest.getSquare());
        }
        if(realEstateRequest.getRooms() != null) {
            realEstate.setRooms(realEstateRequest.getRooms());
        }
        if(realEstateRequest.getFloors() != null) {
            realEstate.setFloors(realEstateRequest.getFloors());
        }
        if(realEstateRequest.getGardenSquare() != null) {
            realEstate.setGardenSquare(realEstateRequest.getGardenSquare());
        }
        if(realEstateRequest.getGarage() != null) {
            realEstate.setGarage(realEstateRequest.getGarage());
        }
        if(realEstateRequest.getAddress() != null) {
            realEstate.setAddress(realEstateRequest.getAddress());
        }
        if(realEstateRequest.getOwnerClientType() != null) {
            realEstate.setOwnerClientType(ClientType.valueOf(realEstateRequest.getOwnerClientType()));
        }
        if(realEstateRequest.getOwner() != null) {
            chooseOwnerByType(realEstateRequest, realEstate);
        }
        if(realEstateRequest.getLocation() != null) {
            setLocation(realEstateRequest, realEstate);
        }
        if(realEstateRequest.getObjectType() != null) {
            setObjectType(realEstateRequest, realEstate);
        }
        if (realEstateRequest.getIsDeleted() != null){
            realEstate.setDeleted(realEstateRequest.getIsDeleted());
        }


        realEstate.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return realEstate;
    }

    public void chooseOwnerByType (RealEstateRequest realEstateRequest, RealEstate realEstate) {
        if(realEstateRequest.getOwnerClientType().equals(ClientType.COMPANY.toString())) {
            realEstate.setOwnerCompany(companyRepository.findById(realEstateRequest.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Company not exist")));
        } else if (realEstateRequest.getOwnerClientType().equals(ClientType.PERSON.toString())) {
            realEstate.setOwnerPerson(personRepository.findById(realEstateRequest.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Person not exist")));
        }
    }

    public void setLocation(RealEstateRequest realEstateRequest, RealEstate realEstate) {
        realEstate.setLocation(locationRepository.findById(realEstateRequest.getLocation())
                .orElseThrow(() -> new EntityNotFoundException("Location not exist")));
    }

    public void setObjectType(RealEstateRequest realEstateRequest, RealEstate realEstate) {
        realEstate.setObjectType(objectTypeRepository.findById(realEstateRequest.getObjectType())
                .orElseThrow(() -> new EntityNotFoundException("ObjectType not exist")));
    }
}
