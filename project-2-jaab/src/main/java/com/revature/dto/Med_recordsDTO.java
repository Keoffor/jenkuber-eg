package com.revature.dto;


import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Med_recordsDTO {
    private int id;

    private String doctor;


    private String patient;


    private Date dateCreated;


    private String notes;
}
