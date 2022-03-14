package com.revature.controller;


import com.revature.dto.DoctorDTO;
import com.revature.dto.PrescriptionDTO;
import com.revature.model.Appointment;
import com.revature.model.Prescription;
import com.revature.service.DoctorService;
import com.revature.utils.DoctorDetails;
import com.revature.utils.Notes;
import com.revature.utils.PatientResponse;
import com.revature.utils.Patientid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospital")
public class DoctorController {

    @Autowired
    DoctorService doctorService;


    @PostMapping(value = "/addDoctor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addDoctor(@RequestBody DoctorDTO em) {
        try {
            doctorService.saveDoctor(em);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully created");
        } catch (Exception e) {

            e.getMessage();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email already exist");
        }


    }
    @PostMapping(value = "/doctorNote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity doctorNote(@RequestBody Notes notes)  {
            try {
                doctorService.medicalRecords(notes);
                return ResponseEntity.status(HttpStatus.OK).body("note submitted successfully");
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("doctors id or patient id is incorrect");
            }
    }

    @PostMapping(value ="/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
            public ResponseEntity doctorAppointment(@RequestBody DoctorDetails d){
        Appointment appointment = doctorService.doctorAppointment(d);
        if(appointment.getDoctor()!=0){
            //TODO rest template for patient appointment here:
            return ResponseEntity.status(HttpStatus.OK).body("Appointment is sent");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("doctors id or patient id is incorrect");
        }
    }

    @GetMapping(value = "/patientRecords", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity userAccount(@RequestBody Patientid em) {


        try {
            PatientResponse response = doctorService.accessPatientRecords(em);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception e){
             e.getMessage();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid patient id");
        }


    }
    @PostMapping(value = "/prescription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity prescription(@RequestBody PrescriptionDTO dto) throws Exception {
        Prescription prep = doctorService.prescription(dto);
        if (prep.getDoctor() == dto.getDoctor_id() && prep.getPatient() ==dto.getPatient_id()){

            //TODO RestTemplate for the prescription and the pharmacist info.
            return ResponseEntity.status(HttpStatus.OK).body("prescription is sent");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid credentials");
        }
    }


}
