package com.revature.service;

import com.revature.dao.*;
import com.revature.dto.DoctorDTO;
import com.revature.dto.PatientNoteDTO;
import com.revature.dto.PrescriptionDTO;
import com.revature.model.*;
import com.revature.model.Status;
import com.revature.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        PatientNote pn = new PatientNote();
        Optional<Doctor> getdoctor = doctorRepository.findById(note.getDoctor_id());
        Doctor doctor = getdoctor.isPresent()?getdoctor.get():null;
        Optional<Patient> getpatient = findPatientbyId(note.getPatient_id());
        Patient patient = getpatient.isPresent()?getpatient.get():null;
        if(doctor!=null && patient !=null){
            pn.setDoctor(doctor);
            pn.setPatient(patient);
            pn.setNotes(note.getNotes());
            patientNoteRepo.save(pn);
        }else{
            logger.info("invalid doctor id or patient id");
            throw new Exception();
        }
        return patient;

    }
    private List<PatientNote> filterPatient(int id){
        List<PatientNote> filter = patientNoteRepo.findByPatient(id);
        return filter;
    }
    private List<PatientNote> filterDoctor(int id){
        List<PatientNote> filter = patientNoteRepo.findByRecordingDoctor(id);

        return filter;

    }
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
            List<PatientNote> p = filterPatient(getTheId.getId());

            List<PatientNoteDTO> get = p.stream().map(e -> {
                PatientNoteDTO patientNoteDTO = new PatientNoteDTO();
                patientNoteDTO.setId(e.getId());
                String patientFullName = e.getPatient().getFirstName()+", "+ e.getPatient().getLastName();
                patientNoteDTO.setPatient(patientFullName);
                String doctorFullName = e.getDoctor().getFirstName() +","+e.getDoctor().getLastName();
                patientNoteDTO.setDoctor(doctorFullName);
                patientNoteDTO.setNotes(e.getNotes());
                patientNoteDTO.setDateCreated(e.getDateCreated());
                return patientNoteDTO;
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
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
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

    public Prescription prescription (PrescriptionDTO pdto){
        Pharmacist pharmacist = getphamEmail(pdto.getPharmacist_email());
        Prescription prescription = new Prescription();
        Optional<Doctor> getdoc = doctorRepository.findById(pdto.getDoctor_id());
        Doctor doctor = getdoc.isPresent()? getdoc.get():null;
        Optional<Patient> getPatient = findPatientbyId(pdto.getPatient_id());
        Patient patient = getPatient.isPresent()? getPatient.get():null;

        if(pharmacist==null){
            logger.info("this email does not exist");
        }else{

        if(doctor!=null & patient !=null){
            prescription.setDoctor(doctor);
            prescription.setPatient(patient);
            prescription.setStatus(Status.PENDING);
            prescription.setDosage(pdto.getDosage());
            prescription.setMedicineName(pdto.getMedicineName());
            prescription.setPharmacist(pharmacist);
            prepRepo.save(prescription);

        }else{
            logger.info("wrong doctor id or patient id");
        }}
        return prescription;



    }

}
