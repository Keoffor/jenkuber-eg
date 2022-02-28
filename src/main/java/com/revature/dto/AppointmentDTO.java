package com.revature.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDTO {
    private Integer id;
    private String schedule;
    private String appointmentTime;
}
