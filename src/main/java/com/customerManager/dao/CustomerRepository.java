package com.customerManager.dao;

import com.customerManager.model.Customer;
import com.customerManager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    @Query("from Customer as c where c.user.id = :userId")
    public Page<Customer> findCustomersByUser(@Param("userId") int userId , Pageable pageable);


    public List<Customer> findByFirstNameContainingAndUser(String firstName, User user);

    public List<Customer> findByEmailContainingAndUser(String email, User user);

    public List<Customer> findByPhoneContainingAndUser(String phone, User user);


   public List<Customer> findByCityContainingAndUser(String city, User user);
}
