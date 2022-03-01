package com.revature.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;


    @Column(name = "requesting_doctor")
    private  int doctor;

    @Column(name = "patient_for")
    private int patient;

    @Column(name = "schedule", nullable = false)
    private String schedule;

    @JsonFormat(pattern ="dd/mm/yyyy")
    @Column(name = "time_of_appointment")
    private Date appointmentTime;

}