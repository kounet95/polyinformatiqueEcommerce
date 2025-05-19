package org.example.queryblog.repos;

import org.example.queryblog.entite.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
