package com.revature.dao;

import com.revature.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Integer> {

    Pharmacist findByEmail(String email);
}