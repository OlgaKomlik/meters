package com.meters.controller;

import com.meters.entities.Manager;
import com.meters.entities.Role;
import com.meters.entities.catalogs.DealType;
import com.meters.entities.catalogs.ObjectType;
import com.meters.exceptoins.ValidationException;
import com.meters.requests.create.RoleRequest;
import com.meters.requests.create.catalogs.DealTypeRequest;
import com.meters.requests.create.catalogs.ObjectTypeRequest;
import com.meters.requests.update.RoleUpdateRequest;
import com.meters.service.CompanyService;
import com.meters.service.DealService;
import com.meters.service.LocationService;
import com.meters.service.ManagerService;
import com.meters.service.PersonService;
import com.meters.service.RealEstateService;
import com.meters.service.RentService;
import com.meters.service.RoleService;
import com.meters.service.SaleService;
import com.meters.service.catalogs.DealTypeService;
import com.meters.service.catalogs.ObjectTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/admin")
@Tag(name = "AdminController", description = "Admin management methods")
@RequiredArgsConstructor
public class AdminController {

    private final RoleService roleService;

    private final ManagerService managerService;

    private final DealTypeService dealTypeService;

    private final ObjectTypeService objectTypeService;

    private final CompanyService companyService;

    private final DealService dealService;

    private final LocationService locationService;

    private final PersonService personService;

    private final RealEstateService realEstateService;

    private final SaleService saleService;

    private final RentService rentService;

    @Operation(
            summary = "Spring Data Roles Find All Search",
            description = "Find All Roles without limitations",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Roles",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Role.class)))

                    ),
                    @ApiResponse(responseCode = "INTERNAL_SERVER_ERROR",
                            description = "Internal Server Error")
            }
    )
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Role by ID",
            description = "Get Role based on specified id",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Role",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Role.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Role with ID not found",
                            content = @Content(
                                    schema = @Schema())
                    )
            }
    )
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRoleById(@Parameter(description = "Role ID", example = "2", required = true) @PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        if (role.isPresent()) {
            return new ResponseEntity<>(role.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Spring Data Create Role",
            description = "Creates a new Role",
            responses = {
                    @ApiResponse(
                            responseCode = "CREATED",
                            description = "Role created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Role.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @PostMapping("/roles")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEntity<Role> createRole(@Valid @RequestBody @Parameter(description = "Role information",
            required = true) RoleRequest roleRequest,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Role role = roleService.createRole(roleRequest);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Role",
            description = "Updating an existing Role",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Role updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Role.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PutMapping("/roles/{id}")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody @Parameter(description = "Role information",
            required = true) RoleUpdateRequest roleRequest, @Parameter(description = "Role ID", example = "5", required = true)
                                           @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Role role = roleService.updateRole(id, roleRequest);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Role (for admins only)",
            description = "Delete role by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Role deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Role not found"
                    )
            }
    )
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteRole(@Parameter(description = "Role ID", example = "5", required = true)
                                             @PathVariable("id") Long id) {
        roleService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Set Role to manager",
            description = "Set role to manager",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Role set successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Manager.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PostMapping("/roles/set/managers/{managerId}")
    public ResponseEntity<Manager> setManagersRole(@Parameter(description = "Manager ID", example = "5", required = true)
                                                   @PathVariable Long managerId,
                                                   @Parameter(description = "Query to search") @RequestParam String roleName) {
        Manager manager = managerService.setUserRole(managerId, roleName);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create Deal Type",
            description = "Creates a new Deal Type",
            responses = {
                    @ApiResponse(
                            responseCode = "CREATED",
                            description = "Deal Type created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DealType.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @PostMapping("/deals/types")
    public ResponseEntity<DealType> createDealType(@Valid @RequestBody @Parameter(description = "Deal Type information",
            required = true) DealTypeRequest dealTypeRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        DealType dealType = dealTypeService.createDealType(dealTypeRequest);
        return new ResponseEntity<>(dealType, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Deal Type",
            description = "Updating an existing Deal Type",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Deal Type updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DealType.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PutMapping("/deals/types/{id}")
    public ResponseEntity<DealType> updateDealType(@Valid @RequestBody @Parameter(description = "Deal Type information",
            required = true) DealTypeRequest dealTypeRequest, @Parameter(description = "Deal Type ID", example = "5",
            required = true) @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        DealType dealType = dealTypeService.updateDealType(id, dealTypeRequest);
        return new ResponseEntity<>(dealType, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Deal Type (for admins only)",
            description = "Delete Deal Type by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Deal Type deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Deal Type not found"
                    )
            }
    )
    @DeleteMapping("/deals/types/{id}")
    public ResponseEntity<String> deleteDealType(@Parameter(description = "Deal Type ID", example = "5", required = true)
                                                 @PathVariable("id") Long id) {
        dealTypeService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create ObjectType",
            description = "Creates a new ObjectType",
            responses = {
                    @ApiResponse(
                            responseCode = "CREATED",
                            description = "ObjectType created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ObjectType.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PostMapping("/objects/types")
    public ResponseEntity<ObjectType> createObjectType(@Valid @RequestBody @Parameter(description = "ObjectType information",
            required = true) ObjectTypeRequest objectTypeRequest,
                                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        ObjectType objectType = objectTypeService.createObjectType(objectTypeRequest);
        return new ResponseEntity<>(objectType, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update ObjectType",
            description = "Updating an existing ObjectType",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "ObjectType updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ObjectType.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PutMapping("/objects/types/{id}")
    public ResponseEntity<ObjectType> updateObjectType(@Valid @RequestBody @Parameter(description = "ObjectType information",
            required = true) ObjectTypeRequest objectTypeRequest, @Parameter(description = "ObjectType ID", example = "5",
            required = true) @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        ObjectType objectType = objectTypeService.updateObjectType(id, objectTypeRequest);
        return new ResponseEntity<>(objectType, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete ObjectType (for admins only)",
            description = "Delete ObjectType by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "ObjectType deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "ObjectType not found"
                    )
            }
    )
    @DeleteMapping("/objects/types/{id}")
    public ResponseEntity<String> deleteObjectType(@Parameter(description = "ObjectType ID", example = "5", required = true)
                                                   @PathVariable("id") Long id) {
        objectTypeService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Company (for admins only)",
            description = "Delete Company by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Company deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Company not found"
                    )
            }
    )
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<String> deleteCompany(@Parameter(description = "Company ID", example = "5", required = true)
                                                @PathVariable("id") Long id) {
        companyService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Deal (for admins only)",
            description = "Delete Deal by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Deal deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Deal not found"
                    )
            }
    )
    @DeleteMapping("/deals/{id}")
    public ResponseEntity<String> deleteDeal(@Parameter(description = "Deal ID", example = "5", required = true)
                                             @PathVariable("id") Long id) {
        dealService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Location (for admins only)",
            description = "Delete Location by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Location deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Location not found"
                    )
            }
    )
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<String> deleteLocation(@Parameter(description = "Location ID", example = "5", required = true)
                                                 @PathVariable("id") Long id) {
        locationService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Manager (for admins only)",
            description = "Delete Manager by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Manager deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Manager not found"
                    )
            }
    )
    @DeleteMapping("/managers/{id}")
    public ResponseEntity<String> deleteManager(@Parameter(description = "Manager ID", example = "5", required = true)
                                                @PathVariable("id") Long id) {
        managerService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Person (for admins only)",
            description = "Delete Person by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Person deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Person not found"
                    )
            }
    )
    @DeleteMapping("/persons/{id}")
    public ResponseEntity<String> deletePerson(@Parameter(description = "Person ID", example = "5", required = true)
                                               @PathVariable("id") Long id) {
        personService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete RealEstate (for admins only)",
            description = "Delete RealEstate by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "RealEstate deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "RealEstate not found"
                    )
            }
    )
    @DeleteMapping("/estates/{id}")
    public ResponseEntity<String> deleteRealEstate(@Parameter(description = "RealEstate ID", example = "5", required = true)
                                                   @PathVariable("id") Long id) {
        realEstateService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Sale (for admins only)",
            description = "Delete Sale by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Sale deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Sale not found"
                    )
            }
    )
    @DeleteMapping("/sales/{id}")
    public ResponseEntity<String> deleteSale(@Parameter(description = "Sale ID", example = "5", required = true)
                                             @PathVariable("id") Long id) {
        saleService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Rent (for admins only)",
            description = "Delete Rent by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Rent deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Rent not found"
                    )
            }
    )
    @DeleteMapping("/rents/{id}")
    public ResponseEntity<String> deleteRent(@Parameter(description = "Rent ID", example = "5", required = true)
                                             @PathVariable("id") Long id) {
        rentService.deleteById(id);
        return new ResponseEntity<>(id + " id is deleted forever", HttpStatus.OK);
    }
}
