package net.elau.example.springboottestcontainers.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import net.elau.example.springboottestcontainers.mapper.CustomerMapper;
import net.elau.example.springboottestcontainers.service.CustomerService;
import net.elau.example.springboottestcontainers.web.request.CreateCustomerRequest;
import net.elau.example.springboottestcontainers.web.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerMapper mapper;

    private final CustomerService service;

    @GetMapping
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable("id") Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody CreateCustomerRequest createCustomerRequest) {
        val customerDTO = service.create(mapper.toDTO(createCustomerRequest));
        return mapper.toResponse(customerDTO);
    }
}
