package com.meters.mappers;

import com.meters.dto.DealDto;
import com.meters.entities.Deal;
import com.meters.entities.enums.ClientType;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.repository.CompanyRepository;
import com.meters.repository.ManagerRepository;
import com.meters.repository.PersonRepository;
import com.meters.repository.RentRepository;
import com.meters.repository.SaleRepository;
import com.meters.repository.catalogs.DealTypeRepository;
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


    public Deal toEntity(DealDto dealDto) {
        Deal deal = modelMapper.map(dealDto, Deal.class);
        setDealType(dealDto, deal);
        setManager(dealDto, deal);
        chooseDealConditions(dealDto, deal);
        chooseBuyer(dealDto, deal);
        chooseOwner(dealDto, deal);

        return deal;
    }

    public DealDto toDto(Deal deal) {
        DealDto dealDto = modelMapper.map(deal, DealDto.class);
        dealDto.setManager(deal.getManager().getId());
        dealDto.setDealType(deal.getDealType().getId());

        if(deal.getRent() != null) {
            dealDto.setDealConditions(deal.getRent().getId());
        } else if (deal.getSale() != null) {
            dealDto.setDealConditions(deal.getSale().getId());
        }

        if(deal.getOwnerCompany() != null) {
            dealDto.setOwner(deal.getOwnerCompany().getId());
        } else if (deal.getOwnerPerson() != null) {
            dealDto.setOwner(deal.getOwnerPerson().getId());
        }

        if(deal.getBuyerCompany() != null) {
            dealDto.setBuyer(deal.getBuyerCompany().getId());
        } else if (deal.getBuyerPerson() != null) {
            dealDto.setBuyer(deal.getBuyerPerson().getId());
        }

        return dealDto;
    }

    public Deal updateDeal(DealDto dealDto, Deal deal) {
        if(dealDto.getAmount() != null) {
            deal.setAmount(dealDto.getAmount());
        }
        if(dealDto.getFee() != null) {
            deal.setFee(dealDto.getFee());
        }
        if(dealDto.getDealDate() != null) {
            deal.setDealDate(dealDto.getDealDate());
        }
        if(dealDto.getOwnerClientType() != null) {
            deal.setOwnerClientType(ClientType.valueOf(dealDto.getOwnerClientType()));
        }
        if(dealDto.getBuyerClientType() != null) {
            deal.setBuyerClientType(ClientType.valueOf(dealDto.getBuyerClientType()));
        }
        if(dealDto.getDealType() != null) {
            setDealType(dealDto, deal);
        }
        if(dealDto.getDealConditions() != null) {
            chooseDealConditions(dealDto, deal);
        }
        if(dealDto.getManager() != null) {
            setManager(dealDto, deal);
        }
        if(dealDto.getOwner() != null) {
            chooseOwner(dealDto, deal);
        }
        if(dealDto.getBuyer() != null) {
            chooseBuyer(dealDto, deal);
        }

        deal.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return deal;
    }

    public void setManager(DealDto dealDto, Deal deal) {
        deal.setManager(managerRepository.findById(dealDto.getManager())
                .orElseThrow(() -> new EntityNotFoundException("Manager not exist")));
    }

    public void setDealType(DealDto dealDto, Deal deal) {
        deal.setDealType(dealTypeRepository.findById(dealDto.getDealType())
                .orElseThrow(() -> new EntityNotFoundException("DealType not exist")));
    }

    public void chooseDealConditions(DealDto dealDto, Deal deal) {
        if(dealDto.getDealType() == DEAL_TYPE_RENT_ID) {
            deal.setRent(rentRepository.findById(dealDto.getDealType())
                    .orElseThrow(() -> new EntityNotFoundException("Rent not exist")));
        } else if (dealDto.getDealType() == DEAL_TYPE_SALE_ID) {
            deal.setSale(saleRepository.findById(dealDto.getDealType())
                    .orElseThrow(() -> new EntityNotFoundException("Sale not exist")));
        }
    }

    public void chooseOwner(DealDto dealDto, Deal deal) {
        if(dealDto.getOwnerClientType().equals(ClientType.COMPANY.toString())) {
            deal.setOwnerCompany(companyRepository.findById(dealDto.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Company not exist")));
        } else if (dealDto.getOwnerClientType().equals(ClientType.PERSON.toString())) {
            deal.setOwnerPerson(personRepository.findById(dealDto.getOwner())
                    .orElseThrow(() -> new EntityNotFoundException("Person not exist")));
        }
    }

    public void chooseBuyer(DealDto dealDto, Deal deal) {
        if(dealDto.getBuyerClientType().equals(ClientType.COMPANY.toString())) {
            deal.setBuyerCompany(companyRepository.findById(dealDto.getBuyer())
                    .orElseThrow(() -> new EntityNotFoundException("Company not exist")));
        } else if (dealDto.getBuyerClientType().equals(ClientType.PERSON.toString())) {
            deal.setBuyerPerson(personRepository.findById(dealDto.getBuyer())
                    .orElseThrow(() -> new EntityNotFoundException("Person not exist")));
        }
    }
}
