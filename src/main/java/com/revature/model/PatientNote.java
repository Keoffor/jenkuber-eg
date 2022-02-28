package com.revature.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PatientNote{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnore
    @ManyToOne(targetEntity = Doctor.class , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "recording_doctor",  referencedColumnName = "id")
    private Doctor doctor;

    @JsonIgnore
    @ManyToOne(targetEntity = Patient.class , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient", referencedColumnName = "id")
    private Patient patient;

    @CreationTimestamp
    @Column(name ="date_of_note")
    private Date dateCreated;

    @Column(name = "notes", nullable = false)
    private String notes;







}
