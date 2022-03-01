package com.revature.controller;

import com.revature.dto.PatientDTO;
import com.revature.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(value = "/doctors/patientInfo/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDTO> getPatientByName(String firstName, String lastName) throws URISyntaxException {

        PatientDTO patientDTO = patientService.getPatientByName(firstName, lastName);
        Integer patientId = patientDTO.getId();

        return ResponseEntity.created(new URI("http://localhost:8880/docors/patientInfo/" + patientId)).build();
    }

    @PatchMapping(value = "/patients/{patientId}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDTO> updateEmail(@PathVariable Integer patientId, String email){

        patientService.updateEmail(patientId, email);

        return ResponseEntity.ok().build();
    }
}
