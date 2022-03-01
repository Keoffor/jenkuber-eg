package com.revature.dto;
import com.revature.model.Status;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private int doctor_id;
    private int patient_id;
    private String medicineName;
    private Float dosage;
    private Status status;
    private String pharmacist_email;
}
