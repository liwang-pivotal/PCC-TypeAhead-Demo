package io.spring.controller;

import io.spring.repo.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeController {
	
	@Autowired
    private EmployeeRepository repository;
    
    @GetMapping("/home")
    public String index(Model model) {
    	
    	model.addAttribute("employees", repository.findAll());
    	
        return "employees";
    }
}
