package com.example.renewnsell.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDTO {

    @NotEmpty(message = "must not be empty")
    private String username;

    @NotEmpty(message = "must not be empty")
    private String password;

    @NotEmpty(message = "must not be empty")
    private String name;

    @Email
    @NotEmpty(message = "must not be empty")
    private String email;

    @NotEmpty(message = "phone must not be empty")
    private String phoneNumber;

    @NotEmpty(message = "commercial License must not be empty")
    private String commercialLicense;
    @NotEmpty(message = "industry License must not be empty")
    private String industry;
    @NotEmpty(message = "logoPath License must not be empty")
    private String logoPath;
}
