package com.audifarma.customer.infrastructure.adapter.in.rest;

import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.domain.port.in.*;
import com.audifarma.customer.infrastructure.adapter.in.rest.dto.*;
import com.audifarma.customer.infrastructure.adapter.in.rest.mapper.ClienteRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Customer management API")
public class ClienteController {

    private final CreateClienteUseCase createClienteUseCase;
    private final GetClienteUseCase getClienteUseCase;
    private final UpdateClienteUseCase updateClienteUseCase;
    private final AddDireccionUseCase addDireccionUseCase;
    private final RemoveDireccionUseCase removeDireccionUseCase;

    public ClienteController(CreateClienteUseCase createClienteUseCase,
                             GetClienteUseCase getClienteUseCase,
                             UpdateClienteUseCase updateClienteUseCase,
                             AddDireccionUseCase addDireccionUseCase,
                             RemoveDireccionUseCase removeDireccionUseCase) {
        this.createClienteUseCase = createClienteUseCase;
        this.getClienteUseCase = getClienteUseCase;
        this.updateClienteUseCase = updateClienteUseCase;
        this.addDireccionUseCase = addDireccionUseCase;
        this.removeDireccionUseCase = removeDireccionUseCase;
    }

    @Operation(summary = "Create a new customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Document number already exists")
    })
    @PostMapping
    public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = createClienteUseCase.create(ClienteRestMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteRestMapper.toResponse(cliente));
    }

    @Operation(summary = "Get customer by ID with all addresses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable Long id) {
        Cliente cliente = getClienteUseCase.getById(id);
        return ResponseEntity.ok(ClienteRestMapper.toResponse(cliente));
    }

    @Operation(summary = "Update customer data (addresses are managed separately)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ClienteUpdateRequest request) {
        Cliente updated = updateClienteUseCase.update(id, ClienteRestMapper.toDomain(id, request));
        return ResponseEntity.ok(ClienteRestMapper.toResponse(updated));
    }

    @Operation(summary = "Add an address to a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Address added"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PostMapping("/{id}/direcciones")
    public ResponseEntity<DireccionResponse> addDireccion(@PathVariable Long id,
                                                          @Valid @RequestBody DireccionRequest request) {
        Direccion direccion = addDireccionUseCase.addDireccion(id, ClienteRestMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteRestMapper.toResponse(direccion));
    }

    @Operation(summary = "Remove an address from a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Address removed"),
            @ApiResponse(responseCode = "404", description = "Customer or address not found")
    })
    @DeleteMapping("/{id}/direcciones/{dirId}")
    public ResponseEntity<Void> removeDireccion(@PathVariable Long id,
                                                @PathVariable Long dirId) {
        removeDireccionUseCase.removeDireccion(id, dirId);
        return ResponseEntity.noContent().build();
    }
}
