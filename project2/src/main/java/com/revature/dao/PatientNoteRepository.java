package com.revature.dao;

import com.revature.model.Med_records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientNoteRepository extends JpaRepository<Med_records, Integer> {
 @Query(value = "select id, date_of_note ,notes,recording_doctor, patient from med_records where patient = ?1", nativeQuery = true)
    List<Med_records> findPatientById(int id);

    @Query(value = "select id, notes,recording_doctor, patient  \n" +
            "from med_records where recording_doctor = ?1", nativeQuery = true)
    List<Med_records> findByRecordingDoctor(int id);
}
