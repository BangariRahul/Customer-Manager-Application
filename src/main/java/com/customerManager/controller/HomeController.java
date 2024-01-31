package com.customerManager.controller;


import com.customerManager.dao.UserRepository;
import com.customerManager.helper.Message;
import com.customerManager.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class HomeController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    // Home page mapping
    @GetMapping("/home")
    public String Home(){
        return "home";
    }


    // Signup page mapping
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title" , "signup page");
        model.addAttribute( "user",new User());
        return "Signup";
    }


    // Register user mapping
    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user , BindingResult result1, @RequestParam(value="agreement",defaultValue = "false")boolean agreement, Model model, HttpSession session){


        try {
            // Check if the user has agreed to the terms and conditions
            if(!agreement){
                System.out.println("you have not agreed the term and conditions...");
                throw new Exception("you have not agreed the term and conditions...");
            }
            // Check for validation errors
            if(result1.hasErrors()){
                System.out.print("ERROR" + result1.toString());
                model.addAttribute("user", user);
                return "Signup";
            }
            // Set default role for the user
            user.setRole("USER");

            // Encode user password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user to the repository
            userRepository.save(user);
            // user attribute and set success message
            model.addAttribute("user", new User());
            session.setAttribute("message" , new Message("successfully registered !! " , "alert-success"));
            return"Signup";
        }
        catch (Exception e){

            // Print stack trace for exception
            e.printStackTrace();
            model.addAttribute("user" , user);
            session.setAttribute("message" , new Message("something went wrong!!" + e.getMessage(), "alert-danger"));
            return"Signup";
        }
    }



}
