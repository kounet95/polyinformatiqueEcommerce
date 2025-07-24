package org.example.queryblog.repos;



import org.example.queryblog.entite.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IteamRepository extends JpaRepository<Item,String> {
}
