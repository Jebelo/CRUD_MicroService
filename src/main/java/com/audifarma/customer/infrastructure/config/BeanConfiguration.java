package com.audifarma.customer.infrastructure.config;

import com.audifarma.customer.application.usecase.*;
import com.audifarma.customer.domain.port.in.*;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;
import com.audifarma.customer.infrastructure.adapter.out.persistence.ClienteRepositoryAdapter;
import com.audifarma.customer.infrastructure.adapter.out.persistence.DireccionRepositoryAdapter;
import com.audifarma.customer.infrastructure.adapter.out.persistence.repository.ClienteJpaRepository;
import com.audifarma.customer.infrastructure.adapter.out.persistence.repository.DireccionJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    // ── Output ports (adapters) ──────────────────────────────────────────────

    @Bean
    public ClienteRepositoryPort clienteRepositoryPort(ClienteJpaRepository clienteJpaRepository) {
        return new ClienteRepositoryAdapter(clienteJpaRepository);
    }

    @Bean
    public DireccionRepositoryPort direccionRepositoryPort(DireccionJpaRepository direccionJpaRepository,
                                                           ClienteJpaRepository clienteJpaRepository) {
        return new DireccionRepositoryAdapter(direccionJpaRepository, clienteJpaRepository);
    }

    // ── Input ports (use cases) ──────────────────────────────────────────────

    @Bean
    public CreateClienteUseCase createClienteUseCase(ClienteRepositoryPort clienteRepository,
                                                     DireccionRepositoryPort direccionRepository) {
        return new CreateClienteService(clienteRepository, direccionRepository);
    }

    @Bean
    public UpdateClienteUseCase updateClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new UpdateClienteService(clienteRepository);
    }

    @Bean
    public GetClienteUseCase getClienteUseCase(ClienteRepositoryPort clienteRepository,
                                               DireccionRepositoryPort direccionRepository) {
        return new GetClienteService(clienteRepository, direccionRepository);
    }

    @Bean
    public AddDireccionUseCase addDireccionUseCase(ClienteRepositoryPort clienteRepository,
                                                   DireccionRepositoryPort direccionRepository) {
        return new AddDireccionService(clienteRepository, direccionRepository);
    }

    @Bean
    public RemoveDireccionUseCase removeDireccionUseCase(ClienteRepositoryPort clienteRepository,
                                                         DireccionRepositoryPort direccionRepository) {
        return new RemoveDireccionService(clienteRepository, direccionRepository);
    }
}
