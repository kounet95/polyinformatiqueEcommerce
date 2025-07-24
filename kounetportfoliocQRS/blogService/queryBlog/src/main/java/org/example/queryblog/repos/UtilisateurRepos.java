package org.example.queryblog.repos;

import org.example.queryblog.entite.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepos extends JpaRepository<Customer,String> {

}
