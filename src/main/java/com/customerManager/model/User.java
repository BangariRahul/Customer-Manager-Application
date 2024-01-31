package com.customerManager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;

    @NotBlank(message = "Name field is required!!")
    @Size(min=2, max=20 , message = "min 2 and max 20 characters are allowed !!" )
    private String name;
    private String email;
    private String password;

    private String Role;

    @OneToMany(cascade = CascadeType.ALL ,fetch =FetchType.LAZY , mappedBy = "user")
    private List<Customer> customer = new ArrayList<>();
}
