package io.spring.repo;

import io.spring.domain.Employee;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

public interface EmployeeRepository extends GemfireRepository<Employee, String> {
	
	@Query("SELECT * FROM /employee e WHERE e.name LIKE $1 LIMIT 5")
	Iterable<Employee> findAFewByNameFuzzy(String name);
}
