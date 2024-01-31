package com.customerManager.controller;


import com.customerManager.dao.CustomerRepository;
import com.customerManager.dao.UserRepository;
import com.customerManager.model.Customer;
import com.customerManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {

       @Autowired
       private UserRepository userRepository;

       @Autowired
       private CustomerRepository customerRepository;

    // Endpoint for searching customers based on the specified type and query
    @GetMapping("/search/{type}/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, @PathVariable("type") String type , Principal principal)
    {
        // List to store the search results
        List<Customer> customers = new ArrayList<>();
        User user = this.userRepository.getUserByUserName(principal.getName());

        // Switch statement to determine the type of search and fetch results accordingly
        switch (type) {
            case "name" -> customers = this.customerRepository.findByFirstNameContainingAndUser(query, user);
            case "email" -> customers = this.customerRepository.findByEmailContainingAndUser(query, user);
            case "phone" -> customers = this.customerRepository.findByPhoneContainingAndUser(query, user);
            case "city" -> customers = this.customerRepository.findByCityContainingAndUser(query, user);
        }


        return ResponseEntity.ok(customers);

    }
}


