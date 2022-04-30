package com.example.api.repository;

import com.example.api.model.Police;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceRepo extends JpaRepository<Police, String> {
}
