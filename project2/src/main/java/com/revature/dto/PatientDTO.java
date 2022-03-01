package com.revature.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String bloodType;
    private String notes;
}
