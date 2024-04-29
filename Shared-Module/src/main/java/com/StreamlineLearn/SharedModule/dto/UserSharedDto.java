package com.StreamlineLearn.SharedModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSharedDto {
    private Long id;
    private String userName;
    private String role;

    public UserSharedDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }
}
