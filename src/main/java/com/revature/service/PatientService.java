package com.revature.service;

import com.revature.dao.PatientRepository;
import com.revature.dto.PatientDTO;
import com.revature.model.Patient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Retrieves patient data based on their first and last name
     * @param firstName - The patient's first name
     * @param lastName - The patient's last name
     * @return - PatientDTO
     */
    public PatientDTO getPatientByName(String firstName, String lastName){
        PatientDTO patientDTO = new PatientDTO();
        Patient patient = patientRepository.getPatientByFirstNameAndLastName(firstName, lastName);
        BeanUtils.copyProperties(patient, patientDTO);
        return patientDTO;
    }

    /**
     * Allows a patient to update their email
     * @param email - The new email
     */
    public void updateEmail(Integer id, String email){
        PatientDTO patientDTO = new PatientDTO();
        Patient patient = patientRepository.getById(id);
        BeanUtils.copyProperties(patient, patientDTO);
        patientRepository.updateEmail(patientDTO.getId(), email);
    }
}
