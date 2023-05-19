package com.meters.controller.catalogs;

import com.meters.requests.catalogs.ObjectTypeRequest;
import com.meters.entities.catalogs.ObjectType;
import com.meters.service.catalogs.ObjectTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/object-types")
@RequiredArgsConstructor
public class ObjectTypeController {

    private final ObjectTypeService objectTypeService;

    @GetMapping
    public ResponseEntity<Object> getAllObjectTypes() {
        List<ObjectType> objectTypes = objectTypeService.findAll();
        return new ResponseEntity<>(objectTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ObjectType>> getObjectTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(objectTypeService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<ObjectType>> createObjectType(@Valid @RequestBody ObjectTypeRequest objectTypeRequest) {
        Optional<ObjectType> objectType = objectTypeService.createObjectType(objectTypeRequest);
        return new ResponseEntity<>(objectType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<ObjectType>> updateObjectType(@Valid @RequestBody ObjectTypeRequest objectTypeRequest, @PathVariable("id") Long id) {
        Optional<ObjectType> objectType = objectTypeService.updateObjectType(id, objectTypeRequest);
        return new ResponseEntity<>(objectType, HttpStatus.OK);
    }

    @PutMapping("/{id}/soft_delete")
    public ResponseEntity<String> deactivateObjectType(@PathVariable("id") Long id) {
        objectTypeService.deactivate(id);
        return new ResponseEntity<>(id + " id is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteObjectType(@PathVariable("id") Long id) {
        objectTypeService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Optional<ObjectType>> restoreDeletedObjectType(@PathVariable("id") Long id) {
        Optional<ObjectType> objectType = objectTypeService.restoreDeletedObjectType(id);
        return new ResponseEntity<>(objectType, HttpStatus.OK);
    }
}
