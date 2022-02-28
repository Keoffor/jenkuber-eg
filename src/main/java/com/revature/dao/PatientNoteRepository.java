package com.revature.dao;

import com.revature.model.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientNoteRepository extends JpaRepository<PatientNote, Integer> {
 @Query(value = "select id, notes,recording_doctor, patient  \n" +
           "from med_records where patient = ?1", nativeQuery = true)
    List<PatientNote> findByPatient(int id);

    @Query(value = "select id, notes,recording_doctor, patient  \n" +
            "from med_records where recording_doctor = ?1", nativeQuery = true)
    List<PatientNote> findByRecordingDoctor(int id);
}
