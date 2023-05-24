package com.meters.controller;

import com.meters.entities.Manager;
import com.meters.exceptoins.ValidationException;
import com.meters.requests.create.ManagerRequest;
import com.meters.requests.update.ManagerUpdateRequest;
import com.meters.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@Tag(name = "ManagerController", description = "Manager management methods")
@RequestMapping("/rest/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @Value("${page-capacity.manager}")
    private Integer pageCapacity;

    @Operation(
            summary = "Spring Data Managers Find All Search",
            description = "Find All Managers without limitations",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Managers",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Manager.class)))

                    ),
                    @ApiResponse(responseCode = "INTERNAL_SERVER_ERROR",
                            description = "Internal Server Error")
            }
    )
    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers() {
        List<Manager> managers = managerService.findAll();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Managers Search with Pageable Params",
            description = "Load page by number with sort and offset params",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Managers",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Manager.class)))

                    ),
                    @ApiResponse(responseCode = "NOT_FOUND",
                            description = "Managers not found")
            }
    )
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Manager>> getAllManagersWithPageAndSort(@Parameter(name = "page", example = "0", required = true) @PathVariable int page) {

        Pageable pageable = PageRequest.of(page, pageCapacity, Sort.by("id").ascending());

        Page<Manager> managers = managerService.findAll(pageable);

        if (managers.hasContent()) {
            return new ResponseEntity<>(managers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Get Manager by ID",
            description = "Get manager based on specified id",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Manager",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Manager.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Manager with ID not found",
                            content = @Content(
                                    schema = @Schema())
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@Parameter(description = "Manager ID", example = "5", required = true) @PathVariable Long id) {

        Optional<Manager> manager = managerService.findById(id);
        if (manager.isPresent()) {
            return new ResponseEntity<>(manager.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Spring Data Create Manager",
            description = "Creates a new Manager",
            responses = {
                    @ApiResponse(
                            responseCode = "CREATED",
                            description = "Manager created",
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
    @PostMapping
    public ResponseEntity<Manager> createManager(@Valid @RequestBody @Parameter(description = "Manager information", required = true) ManagerRequest managerRequest,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Manager manager = managerService.createManager(managerRequest);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Manager",
            description = "Updating an existing Manager",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Manager updated successfully",
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
    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@Valid @RequestBody @Parameter(description = "Manager information",
            required = true) ManagerUpdateRequest managerRequest, @Parameter(description = "Manager ID", example = "5", required = true)
                                                 @PathVariable("id") Long id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Manager manager = managerService.updateManager(id, managerRequest);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }


    @Operation(
            summary = "Get Birth day Managers",
            description = "Get managers who has birth day today",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Managers found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Manager.class)
                            )
                    ),
                    @ApiResponse(responseCode = "NOT_FOUND",
                            description = "Managers not found"
                    )
            }
    )
    @GetMapping("/stat/birthday")
    public ResponseEntity<List<Manager>> getBirthDayManagers() {
        List<Manager> managers = managerService.findBirthDayManagers(LocalDateTime.now());
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Managers with average fee",
            description = "Get managers with their average fee ordering from the best fee",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Managers found",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(responseCode = "INTERNAL_SERVER_ERROR",
                            description = "Internal Server Error")
            }
    )
    @GetMapping("/stat/fee")
    public ResponseEntity<Map<String, Double>> getAverageFeeOfManagers() {
        Map<String, Double> statistic = managerService.getAverageFeeOfManagers();
        return ResponseEntity.ok(statistic);
    }

    @Operation(
            summary = "Get Managers by part of Full Name",
            description = "Get managers by part of Full Name ignore Case",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Managers found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Manager.class)
                            )
                    ),
                    @ApiResponse(responseCode = "NOT_FOUND",
                            description = "Managers not found"
                    )
            }
    )
    @GetMapping("/search/fullname")
    public ResponseEntity<List<Manager>> findByFullName(@Parameter(description = "Query to search", example = "Olga K") @RequestParam String query) {
        List<Manager> managers = managerService.findByFullNameContainingIgnoreCase(query);
        return ResponseEntity.ok(managers);
    }

    @Operation(
            summary = "Get rating of Best Sellers of the month",
            description = "Get Managers who have the biggest fee from deals in period you choose",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Managers found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Manager.class)
                            )
                    ),
                    @ApiResponse(responseCode = "NOT_FOUND",
                            description = "Managers not found"
                    )
            }
    )
    @GetMapping("/stat/sellers")
    public ResponseEntity<List<Manager>> findBestSellers(@Parameter(description = "month value", example = "5", required = true) @RequestParam int month,
                                                         @Parameter(description = "year value", example = "2023", required = true) @RequestParam int year) {
        return ResponseEntity.ok(managerService.getBestSellersOfTheMonth(month, year));
    }
}
