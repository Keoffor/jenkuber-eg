package com.revature.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "med_records")
public class Med_records {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @Column(name = "recording_doctor")
    private int doctor;


    @Column(name = "patient")
    private int patient;

    @Column(name ="date_of_note")
    private Date dateCreated;

    @Column(name = "notes", nullable = false)
    private String notes;







}
