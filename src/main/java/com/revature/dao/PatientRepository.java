package com.revature.dao;

import com.revature.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("FROM Patient p where p.firstName like :firstName or p.lastName like :lastName")
    Patient getPatientByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Modifying
    @Query("update Patient  p set p.email = :email where p.id = :patientId")
    void updateEmail(@Param("patientId") Integer patientId, @Param("email") String email);

}