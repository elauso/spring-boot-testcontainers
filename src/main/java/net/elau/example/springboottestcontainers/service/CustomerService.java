package net.elau.example.springboottestcontainers.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.elau.example.springboottestcontainers.dto.CreateCustomerDTO;
import net.elau.example.springboottestcontainers.dto.CustomerDTO;
import net.elau.example.springboottestcontainers.mapper.CustomerMapper;
import net.elau.example.springboottestcontainers.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerMapper mapper;

    private final CustomerRepository repository;

    public Page<CustomerDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    public CustomerDTO findById(Long id) {
        val customer = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.toDTO(customer);
    }

    @Transactional
    public CustomerDTO create(CreateCustomerDTO createCustomerDTO) {
        val customer = mapper.toModel(createCustomerDTO);
        return mapper.toDTO(repository.save(customer));
    }
}
