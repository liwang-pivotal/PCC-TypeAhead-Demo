package io.spring.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.spring.domain.Employee;
import io.spring.repo.EmployeeRepository;

import java.util.Iterator;

import javax.annotation.PostConstruct;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private ClientCache clientCache;
	
	Fairy fairy = Fairy.create();
	
	@RequestMapping(method = RequestMethod.GET, path = "/create")
	@ResponseBody
	public String create() throws Exception {

		Person person = fairy.person();
		Employee customer = new Employee(person.passportNumber(), person.firstName(), person.lastName());
		repository.save(customer);

		return "New Employee Info Created! <Customer>: " + customer;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/show")
	@ResponseBody
	public String show() throws Exception {
		String resultString = "";
		
		Iterator<?> iter = repository.findAll().iterator();		
		while (iter.hasNext()) {
			resultString += iter.next().toString() + "<br/>";
		}

		return resultString;
	}
	
	@RequestMapping(value = "/employees", method = GET)
    public Iterable<Employee> getName(@Param("name") String name) {
		return repository.findAFewByNameFuzzy(name);
	}
	
	@PostConstruct
	public String load() throws Exception {

		for (int i=0; i<50; i++) {
			create();
		}

		return "New 50 Employees Info Created!";
	}
	
}
