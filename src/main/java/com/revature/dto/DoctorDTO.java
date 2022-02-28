package com.revature.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DoctorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
