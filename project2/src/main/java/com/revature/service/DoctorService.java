package com.revature.service;

import com.revature.dao.*;
import com.revature.dto.DoctorDTO;
import com.revature.dto.Med_recordsDTO;
import com.revature.dto.PrescriptionDTO;
import com.revature.model.*;
import com.revature.model.Status;
import com.revature.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientNoteRepository patientNoteRepo;
    @Autowired
    AppointmentRepository appointRepo;

    @Autowired
    PrescriptionRepository prepRepo;

    @Autowired
    PharmacistRepository phrepo;

    public Doctor saveDoctor(DoctorDTO em) throws Exception {


        Doctor e = new Doctor();
        Doctor checkEmail = doctorRepository.findByEmail(em.getEmail());
        if(checkEmail!=null)
        {
            logger.info("email already exist");
            throw new Exception();
        }
        else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(em.getPassword());

            e.setId(em.getId());
            e.setEmail(em.getEmail());
            e.setFirstName(em.getFirstName());
            e.setLastName(em.getLastName());
            e.setPassword(encodedPassword);
            e.setRoles(Role.PHYSICIAN);
            doctorRepository.save(e);
            logger.info("user was added successfully");
        }

        return e;

    }
    public Optional<Patient> findPatientbyId(int id){
        return patientRepository.findById(id);
    }

    public Patient medicalRecords(Notes note) throws Exception{
        Med_records pn = new Med_records();
        Optional<Doctor> getdoctor = doctorRepository.findById(note.getDoctor_id());
        Doctor doctor = getdoctor.isPresent()?getdoctor.get():null;
        Optional<Patient> getpatient = findPatientbyId(note.getPatient_id());
        Patient patient = getpatient.isPresent()?getpatient.get():null;
        if(doctor!=null && patient !=null){
            pn.setDoctor(doctor.getId());
            pn.setPatient(patient.getId());
            pn.setNotes(note.getNotes());
            pn.setDateCreated(new Date());
            patientNoteRepo.save(pn);
        }else{
            logger.info("invalid doctor id or patient id");
            throw new Exception();
        }
        return patient;

    }
    private List<Med_records> filterPatient(int id){
        List<Med_records> filter = patientNoteRepo.findPatientById(id);
        return filter;
    }
//    private List<Med_records> filterDoctor(int id){
//        List<Med_records> filter = patientNoteRepo.findByRecordingDoctor(id);
//
//        return filter;
//
//    }
    public PatientResponse accessPatientRecords(Patientid id) throws Exception{
        Optional<Patient> check = findPatientbyId(id.getPatient_id());

        PatientResponse pr = new PatientResponse();
        Patient getTheId = check.isPresent()?check.get():null;


        if(getTheId==null){
            logger.info("invalid patient id");
            throw new Exception();
        }else{
            pr.setEmail(getTheId.getEmail());
            pr.setFirstName(getTheId.getFirstName());
            pr.setLastName(getTheId.getLastName());
            String patientFullName = pr.getFirstName() + " " + pr.getLastName();
            List<Med_records> p = filterPatient(getTheId.getId());

            List<Med_recordsDTO> get = p.stream().map(e -> {
                Med_recordsDTO medrecords = new Med_recordsDTO();
                medrecords.setId(e.getId());

                medrecords.setPatient(patientFullName);
                Optional<Doctor> getdoc = doctorRepository.findById(e.getDoctor());
                if(getdoc.isPresent()){
                    String fullName = getdoc.get().getFirstName() +" " + getdoc.get().getLastName();
                    medrecords.setDoctor(fullName);
                }
                medrecords.setDateCreated(new Date());
                medrecords.setNotes(e.getNotes());
                return medrecords;
            }).collect(Collectors.toList());

            pr.setPatientNotes(get);

        }
        return pr;

    }
    public Appointment doctorAppointment(DoctorDetails detail){
        Appointment appointment = new Appointment();
        Optional<Doctor> getdoc = doctorRepository.findById(detail.getDoctor_id());
        Doctor doctor = getdoc.isPresent()? getdoc.get():null;
        Optional<Patient> getPatient = findPatientbyId(detail.getPatient_id());
        Patient patient = getPatient.isPresent()? getPatient.get():null;
        if(doctor!=null && patient !=null){
            appointment.setDoctor(doctor.getId());
            appointment.setPatient(patient.getId());
            appointment.setSchedule(detail.getSchedule());
            appointment.setAppointmentTime(detail.getAppointmentTime());
            appointRepo.save(appointment);
        }else{
            logger.info("invalid doctor id or patient id");
        }
        return appointment;


    }
    private Pharmacist getphamEmail (String email){
        return phrepo.findByEmail(email);

    }

    public Prescription prescription (PrescriptionDTO pdto) {
        Pharmacist pharmacist = getphamEmail(pdto.getPharmacist_email());
        Prescription prescription = new Prescription();
        Optional<Doctor> getdoc = doctorRepository.findById(pdto.getDoctor_id());
        Doctor doctor = getdoc.isPresent() ? getdoc.get() : null;
        Optional<Patient> getPatient = findPatientbyId(pdto.getPatient_id());
        Patient patient = getPatient.isPresent() ? getPatient.get() : null;

        if (pharmacist == null) {
            logger.info("this email does not exist");
        } else {
            if (doctor != null && patient != null) {
                prescription.setDoctor(doctor.getId());
                prescription.setPatient(patient.getId());
                prescription.setStatus(Status.PENDING);
                prescription.setDosage(pdto.getDosage());
                prescription.setMedicineName(pdto.getMedicineName());
                prescription.setPharmacistEmail(pharmacist.getEmail());
                prescription.setPharmacistName(pharmacist.getFirstName());
                prepRepo.save(prescription);

            } else {
                logger.info("doctor id/patient is invalid");
            }
        }

            return prescription;
    }
}