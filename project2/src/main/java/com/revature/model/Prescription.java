package com.revature.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "requesting_doctor")
    private int doctor;

    @Column(name = "patient_for")
    private int patient;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @Column(name = "cc", nullable = false)
    private Float dosage;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private String pharmacistEmail ;

    @Transient
    private String pharmacistName;
}