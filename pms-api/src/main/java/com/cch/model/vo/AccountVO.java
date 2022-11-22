package com.cch.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class AccountVO {
    @NotBlank(message = "Please enter")
    private String username;
    @NotBlank(message = "Please enter")
    private String password;
    @NotBlank(message = "Please enter")
    private String code;
}
