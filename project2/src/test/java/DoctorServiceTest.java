import com.revature.dao.*;
import com.revature.dto.DoctorDTO;
import com.revature.dto.PrescriptionDTO;
import com.revature.model.*;
import com.revature.service.DoctorService;
import com.revature.utils.DoctorDetails;
import com.revature.utils.Notes;
import com.revature.utils.PatientResponse;
import com.revature.utils.Patientid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@Profile("test")
@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    PatientRepository patientRepository;

    @Mock
    PatientNoteRepository patientNoteRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    PrescriptionRepository preprepository;

    @Mock
    PharmacistRepository pharmacistRepository;

    @InjectMocks
    DoctorService doctorService;

    Doctor doctor = new Doctor(2,"Frank","Okro","email.com","password", Role.PHYSICIAN);
    DoctorDTO doctorDTO = new DoctorDTO(1, "george","Same","john","ddkdk");
    Patient patient = new Patient(2,"Grace","Emma","slsl@yahoo.com","dhd",900L,"A");
    Appointment appointment = new Appointment(1,2,7,"james",new Date());
    Pharmacist pharmacist = new Pharmacist();
    Notes notes = new Notes();
    DoctorDetails doctorDetails = new DoctorDetails();
    Prescription prescription = new Prescription();
    PrescriptionDTO prepDTO = new PrescriptionDTO();


    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addDoctorTest() throws Exception {

        Mockito.when(doctorRepository.findByEmail(any())).thenReturn(null);
        Mockito.when(doctorRepository.save(doctor)).thenReturn(doctor);
        Doctor testDoc = doctorService.saveDoctor(doctorDTO);
        Assert.assertEquals(testDoc.getFirstName(), doctorDTO.getFirstName());
        Assert.assertEquals(testDoc.getLastName(),doctorDTO.getLastName());

    }
    @Test(expected = Exception.class)
    public void addDoctorTestShouldReturnError() throws Exception {
        Mockito.when(doctorRepository.findByEmail(any())).thenReturn(doctor);
        doctorService.saveDoctor(doctorDTO);

    }

  @Test
  public void medicalRecordsTest() throws Exception {
        Mockito.when(doctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));
        Mockito.when(patientRepository.findById(anyInt())).thenReturn(Optional.of(patient));
        Patient pat = doctorService.medicalRecords(notes);
        Assert.assertEquals(pat.getFirstName(),"Grace");
  }

  @Test(expected = Exception.class)
        public void medicalRecordsTestShouldReturnError() throws Exception {
       Mockito.when(doctorRepository.findById(anyInt())).thenReturn(Optional.empty());
      Mockito.when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());
      Patient pat = doctorService.medicalRecords(notes);
      Assert.assertEquals(pat.getFirstName(),null);

     }
 @Test
 public void accessPatientRecordsTest() throws Exception {
     Patientid pr = new Patientid();
     Mockito.when(patientRepository.findById(anyInt())).thenReturn(Optional.of(patient));
     PatientResponse res = doctorService.accessPatientRecords(pr);
     Assert.assertEquals(res.getEmail(),"slsl@yahoo.com");

 }
 @Test(expected = Exception.class)
    public void accessPatientRecordsShouldReturnError() throws Exception {
     Patientid pr = new Patientid();
        Mockito.when((patientRepository.findById(anyInt()))).thenReturn(Optional.empty());
        PatientResponse res = doctorService.accessPatientRecords(pr);
     Assert.assertNull(res.getFirstName());
 }

 @Test
    public void doctorAppointmentTest(){
     Mockito.when(doctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));
     Mockito.when(patientRepository.findById(anyInt())).thenReturn(Optional.of(patient));
     Mockito.when(appointmentRepository.save(appointment)).thenReturn(appointment);
     Appointment appoint = doctorService.doctorAppointment(doctorDetails);
     Assert.assertEquals(appoint.getPatient(),2);

 }

 @Test
    public void doctorAppointmentTestShouldReturnError(){
     Mockito.when(doctorRepository.findById(anyInt())).thenReturn(Optional.empty());
     Mockito.when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());
     doctorService.doctorAppointment(doctorDetails);

 }

@Test
    public void prescriptionTest() throws Exception {
    Mockito.when(pharmacistRepository.findByEmail(any())).thenReturn(pharmacist);
    Mockito.when(doctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));
    Mockito.when(patientRepository.findById(anyInt())).thenReturn(Optional.of(patient));
    Mockito.when(preprepository.save(prescription)).thenReturn(prescription);
    Prescription getprep = doctorService.prescription(prepDTO);
    Assert.assertEquals(getprep.getDoctor(),2);

    }
@Test
    public void setPrescriptionTestShouldReturnError() throws Exception {
        Mockito.when(pharmacistRepository.findByEmail(any())).thenReturn(null);
        Mockito.when(doctorRepository.findById(anyInt())).thenReturn(Optional.empty());
        Mockito.when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());
        doctorService.prescription(prepDTO);

}


  }







