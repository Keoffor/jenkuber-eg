package com.revature.dao;

import com.revature.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmail(String email);

    @Query(value = "select id, email,first_name,last_name, password, roles from doctor where id = ?1", nativeQuery = true)
    Optional<Doctor> findById(int id);
}