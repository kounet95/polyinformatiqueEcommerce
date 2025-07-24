package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.SocialGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialGroupRepository extends JpaRepository<SocialGroup, String> {
}