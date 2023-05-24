package com.meters.service.impl;

import com.meters.entities.Deal;
import com.meters.entities.Manager;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.DealMapper;
import com.meters.repository.DealRepository;
import com.meters.requests.create.DealRequest;
import com.meters.requests.update.DealUpdateRequest;
import com.meters.service.DealService;
import com.meters.service.email.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DealServiceImpl implements DealService {

    private static final Logger logger = Logger.getLogger(DealServiceImpl.class);

    private final DealRepository dealRepository;

    private final DealMapper dealMapper;

    private final EmailSenderService emailSenderService;

    @Override
    @Transactional
    public Deal createDeal(DealRequest dealRequest) {
        Deal deal = dealMapper.toEntity(dealRequest);
        Manager manager = deal.getManager();
        String subject = "Congratulations on a successful transaction!";
        String message = "Congratulations on the successful transaction. The income from the transaction amounted to "
                + deal.getFee() + " dollars. Become employee of the month and get a bonus.\n" +
                "We believe in you " + manager.getFullName() + "\nBest regards, successful sales. Meters team";
        try {
            emailSenderService.sendEmail(manager.getAuthenticationInfo().getEmail(), subject, message);
        } catch (MailException exception) {
            logger.error("Message not sending");
        }
        return dealRepository.save(deal);
    }

    @Override
    @Transactional
    public Deal updateDeal(Long id, DealUpdateRequest dealRequest) {
        Deal deal = findDeal(id);
        dealMapper.updateDeal(dealRequest, deal);
        return dealRepository.save(deal);
    }

    @Override
    public List<Deal> findAll() {
        return dealRepository.findAll();
    }

    @Override
    public Page<Deal> findAll(Pageable pageable) {
        return dealRepository.findAll(pageable);
    }

    @Override
    public Optional<Deal> findById(Long id) {
        Deal deal = findDeal(id);
        if (deal.isDeleted()) {
            throw new EntityIsDeletedException("Deal is deleted");
        }
        return Optional.of(deal);
    }

    @Override
    public void deleteById(Long id) {
        dealRepository.deleteById(id);
    }

    private Deal findDeal(Long id) {
        return dealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deal could not be found"));
    }
}
