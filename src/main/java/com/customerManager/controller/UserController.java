package com.customerManager.controller;

import com.customerManager.dao.CustomerRepository;
import com.customerManager.dao.UserRepository;
import com.customerManager.helper.Message;
import com.customerManager.model.Customer;
import com.customerManager.model.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;


    // User dashboard page with pagination
    @RequestMapping("/index/{page}")
    public String dashboard(@PathVariable("page") Integer page, Model model , Principal principal) {
        model.addAttribute("title", "User Dashboard");
        User user = userRepository.getUserByUserName(principal.getName());

        // Fetch customers with pagination
        Pageable pageable = PageRequest.of(page , 5);
        Page<Customer> customer =customerRepository.findCustomersByUser(user.getUId() , pageable);
        model.addAttribute("customer" , customer);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customer.getTotalPages());
        model.addAttribute("title" , "User Dashboard");
        return "normal/user_dashboard";
    }


    // Add customer page
    @RequestMapping("/add_contact")
    public String addContact(Model model){
        model.addAttribute("title", "Add customer");
        model.addAttribute("customer" , new Customer());
        return "normal/add_customer";
    }

    // Process adding a customer
    @PostMapping("/process-customer")
    public String processCostumer(
            @ModelAttribute Customer customer
            ,Principal principal,
            HttpSession session
    ){

        try {

            String name = principal.getName();
            User user = userRepository.getUserByUserName(name);

            // Add customer to the user's list of customers
            user.getCustomer().add(customer);
            customer.setUser(user);
            userRepository.save(user);


            session.setAttribute("message", new Message("Your Contact is added", "success"));
        }catch (Exception e){
            System.out.println("Error" + e.getMessage());
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong !! Try again", "danger"));

        }
        return "normal/add_customer";
    }

    // Delete customer
    @GetMapping("/delete/{cId}")
    public String deleteCustomer(@PathVariable("cId") int cId  , Principal principal){

        customerRepository.deleteById(cId);
        return "redirect:/user/index/0";

    }

    // Update customer form
       @PostMapping("/update-customer/{cId}")
       public String  updateCustomer(@PathVariable("cId") int cId , Model model){

        Customer customer = customerRepository.findById(cId).get();

        model.addAttribute("customer", customer);
        return "normal/update_customer";
       }

    // Process updating customer
    @PostMapping("update-customer")
    public String processUpdate(@ModelAttribute Customer customer , Principal principal){

        User user = userRepository.getUserByUserName(principal.getName());
        customer.setUser(user);
        customerRepository.save(customer);
        return "redirect:/user/index/0";
    }

    // Show details of a single customer
    @GetMapping("showCustomer/{cId}")
    public String showCustomer(@PathVariable("cId") Integer cId, Model model ){

        Customer customer = customerRepository.findById(cId).get();

        model.addAttribute("customer" , customer);
        return "/normal/single_customer";
    }
}
