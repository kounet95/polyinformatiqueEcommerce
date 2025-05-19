package org.example.queryblog.repos;

import org.example.queryblog.entite.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, String> {

}
