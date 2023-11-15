package net.elau.example.springboottestcontainers.repository;

import net.elau.example.springboottestcontainers.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>, PagingAndSortingRepository<Customer, Long> {
}
