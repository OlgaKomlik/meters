package com.meters.controller.catalogs;

import com.meters.requests.create.catalogs.ObjectTypeRequest;
import com.meters.entities.catalogs.ObjectType;
import com.meters.service.catalogs.ObjectTypeService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/object-types")
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
        Optional<ObjectType > objectType = objectTypeService.findById(id);
        if(objectType.isPresent()) {
            return new ResponseEntity<> (objectType.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ObjectType> createObjectType(@Valid @RequestBody ObjectTypeRequest objectTypeRequest,
                                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        ObjectType objectType = objectTypeService.createObjectType(objectTypeRequest);
        return new ResponseEntity<>(objectType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectType> updateObjectType(@Valid @RequestBody ObjectTypeRequest objectTypeRequest,
                                                       @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        ObjectType objectType = objectTypeService.updateObjectType(id, objectTypeRequest);
        return new ResponseEntity<>(objectType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteObjectType(@PathVariable("id") Long id) {
        objectTypeService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }
}
