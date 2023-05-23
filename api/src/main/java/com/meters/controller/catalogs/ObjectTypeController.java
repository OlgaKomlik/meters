package com.meters.controller.catalogs;


import com.meters.entities.catalogs.ObjectType;
import com.meters.service.catalogs.ObjectTypeService;
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
@RequestMapping("/rest/objects/types")
@Tag(name = "ObjectTypeController", description = "Object type management methods")
@RequiredArgsConstructor
public class ObjectTypeController {

    private final ObjectTypeService objectTypeService;

    @GetMapping
    public ResponseEntity<List<ObjectType>> getAllObjectTypes() {
        List<ObjectType> objectTypes = objectTypeService.findAll();
        return new ResponseEntity<>(objectTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectType> getObjectTypeById(@PathVariable Long id) {
        Optional<ObjectType> objectType = objectTypeService.findById(id);
        if (objectType.isPresent()) {
            return new ResponseEntity<>(objectType.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
