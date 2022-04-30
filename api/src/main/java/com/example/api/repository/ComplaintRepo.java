package com.example.api.repository;

import com.example.api.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint,String> {

    List<Complaint> findAllByOrderByDateDesc();
    List<Complaint> findAllByLocationAndPoliceInvolved(String location, boolean isPoliceInvolved);
}
