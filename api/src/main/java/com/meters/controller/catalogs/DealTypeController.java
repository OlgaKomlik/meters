package com.meters.controller.catalogs;

import com.meters.entities.catalogs.DealType;
import com.meters.service.catalogs.DealTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/deals/types")
@Tag(name = "DealTypeController", description = "Deal type management methods")
@RequiredArgsConstructor
public class DealTypeController {

    private final DealTypeService dealTypeService;

    @GetMapping
    public ResponseEntity<List<DealType>> getAllDealTypes() {
        List<DealType> dealTypes = dealTypeService.findAll();
        return new ResponseEntity<>(dealTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealType> getDealTypeById(@PathVariable Long id) {
        Optional<DealType> dealType = dealTypeService.findById(id);
        if (dealType.isPresent()) {
            return new ResponseEntity<>(dealType.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
