package com.meters.service.impl;

import com.meters.entities.RealEstate;
import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import com.meters.mappers.RealEstateMapper;
import com.meters.repository.RealEstateRepository;
import com.meters.requests.create.RealEstateRequest;
import com.meters.requests.update.RealEstateUpdateRequest;
import com.meters.service.RealEstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RealEstateServiceImpl implements RealEstateService {

    private final RealEstateRepository realEstateRepository;

    private final RealEstateMapper realEstateMapper;

    @Override
    @Transactional
    public RealEstate createRealEstate(RealEstateRequest realEstateRequest) {
        RealEstate realEstate = realEstateMapper.toEntity(realEstateRequest);
        return realEstateRepository.save(realEstate);
    }

    @Override
    @Transactional
    public RealEstate updateRealEstate(Long id, RealEstateUpdateRequest realEstateRequest) {
        RealEstate realEstate = findRealEstate(id);
        realEstateMapper.updateRealEstate(realEstateRequest, realEstate);
        return realEstateRepository.save(realEstate);
    }

    @Override
    public List<RealEstate> findAll() {
        return realEstateRepository.findAll();
    }

    @Override
    public Page<RealEstate> findAll(Pageable pageable) {
        return realEstateRepository.findAll(pageable);
    }

    @Override
    public Optional<RealEstate> findById(Long id) {
        RealEstate realEstate = findRealEstate(id);
        if (realEstate.isDeleted()) {
            throw new EntityIsDeletedException("RealEstate is deleted");
        }
        return Optional.of(realEstate);
    }

    @Override
    public void deleteById(Long id) {
        realEstateRepository.deleteById(id);
    }


    private RealEstate findRealEstate(Long id) {
        return realEstateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("RealEstate could not be found"));
    }
}
