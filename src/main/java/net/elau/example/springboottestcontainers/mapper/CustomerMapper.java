package net.elau.example.springboottestcontainers.mapper;

import net.elau.example.springboottestcontainers.domain.Customer;
import net.elau.example.springboottestcontainers.dto.CreateCustomerDTO;
import net.elau.example.springboottestcontainers.dto.CustomerDTO;
import net.elau.example.springboottestcontainers.web.request.CreateCustomerRequest;
import net.elau.example.springboottestcontainers.web.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toModel(CreateCustomerDTO createCustomerDTO);

    CustomerDTO toDTO(Customer customer);

    CustomerResponse toResponse(CustomerDTO customerDTO);

    CreateCustomerDTO toDTO(CreateCustomerRequest createCustomerRequest);
}
