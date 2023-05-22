package com.meters.mappers;

import com.meters.requests.create.DealRequest;
import com.meters.entities.Deal;
import com.meters.entities.constants.ClientType;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.CompanyRepository;
import com.meters.repository.ManagerRepository;
import com.meters.repository.PersonRepository;
import com.meters.repository.RentRepository;
import com.meters.repository.SaleRepository;
import com.meters.repository.catalogs.DealTypeRepository;
import com.meters.requests.update.DealUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DealMapper {
    private static final Long DEAL_TYPE_RENT_ID = 2L;
    private static final Long DEAL_TYPE_SALE_ID= 1L;


    private final ModelMapper modelMapper;
    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;
    private final ManagerRepository managerRepository;
    private final DealTypeRepository dealTypeRepository;
    private final RentRepository rentRepository;
    private final SaleRepository saleRepository;


    public Deal toEntity(DealRequest dealRequest) {
        Deal deal = modelMapper.map(dealRequest, Deal.class);
        setDealType(dealRequest, deal);
        setManager(dealRequest, deal);
        chooseDealConditions(dealRequest, deal);
        chooseBuyer(dealRequest, deal);
        chooseOwner(dealRequest, deal);

        return deal;
    }


    public Deal updateDeal(DealUpdateRequest dealRequest, Deal deal) {
        if(dealRequest.getAmount() != null) {
            deal.setAmount(dealRequest.getAmount());
        }
        if(dealRequest.getFee() != null) {
            deal.setFee(dealRequest.getFee());
        }
        if(dealRequest.getDealDate() != null) {
            deal.setDealDate(dealRequest.getDealDate());
        }
        if(dealRequest.getOwnerClientType() != null) {
            deal.setOwnerClientType(ClientType.valueOf(dealRequest.getOwnerClientType()));
        }
        if(dealRequest.getBuyerClientType() != null) {
            deal.setBuyerClientType(ClientType.valueOf(dealRequest.getBuyerClientType()));
        }
        if(dealRequest.getDealType() != null) {
            setDealType(dealRequest, deal);
        }
        if(dealRequest.getDealConditions() != null) {
            chooseDealConditions(dealRequest, deal);
        }
        if(dealRequest.getManager() != null) {
            setManager(dealRequest, deal);
        }
        if(dealRequest.getOwner() != null) {
            chooseOwner(dealRequest, deal);
        }
        if(dealRequest.getBuyer() != null) {
            chooseBuyer(dealRequest, deal);
        }

        if (dealRequest.getIsDeleted() != null){
            deal.setDeleted(dealRequest.getIsDeleted());
        }

        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return deal;
    }

    public void setManager(DealRequest dealRequest, Deal deal) {
        deal.setManager(managerRepository.findById(dealRequest.getManager())
                .orElseThrow(() -> new EntityNotFoundException("Manager not exist")));
    }

    public void setDealType(DealRequest dealRequest, Deal deal) {
        deal.setDealType(dealTypeRepository.findById(dealRequest.getDealType())
                .orElseThrow(() -> new EntityNotFoundException("DealType not exist")));
    }

    public void chooseDealConditions(DealRequest dealRequest, Deal deal) {
        if(dealRequest.getDealType() == DEAL_TYPE_RENT_ID) {
            deal.setRent(rentRepository.findById(dealRequest.getDealType())
                    .orElseThrow(() -> new EntityNotFoundException("Rent not exist")));
        } else if (dealRequest.getDealType() == DEAL_TYPE_SALE_ID) {
            deal.setSale(saleRepository.findById(dealRequest.getDealType())
                    .orElseThrow(() -> new EntityNotFoundException("Sale not exist")));
        }
    }

    public void chooseOwner(DealRequest dealRequest, Deal deal) {
        if(dealRequest.getOwnerClientType().equals(ClientType.COMPANY.toString())) {
            deal.setOwnerCompany(companyRepository.findById(dealRequest.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Company not exist")));
        } else if (dealRequest.getOwnerClientType().equals(ClientType.PERSON.toString())) {
            deal.setOwnerPerson(personRepository.findById(dealRequest.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Person not exist")));
        }
    }

    public void chooseBuyer(DealRequest dealRequest, Deal deal) {
        if(dealRequest.getBuyerClientType().equals(ClientType.COMPANY.toString())) {
            deal.setBuyerCompany(companyRepository.findById(dealRequest.getBuyer())
                    .orElseThrow(() -> new EntityNotFoundException("Company not exist")));
        } else if (dealRequest.getBuyerClientType().equals(ClientType.PERSON.toString())) {
            deal.setBuyerPerson(personRepository.findById(dealRequest.getBuyer())
                    .orElseThrow(() -> new EntityNotFoundException("Person not exist")));
        }
    }
}
