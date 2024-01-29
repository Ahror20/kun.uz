package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCRUDDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private ProfileStatus status;
    private ProfileRole role;
}
