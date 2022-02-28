package com.revature.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDetails {
    private int doctor_id;
    private int patient_id;
    private String schedule;
    @JsonFormat(pattern ="dd/mm/yyyy")
    private Date appointmentTime;
}
