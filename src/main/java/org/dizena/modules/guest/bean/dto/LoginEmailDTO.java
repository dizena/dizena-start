package org.dizena.modules.guest.bean.dto;

import lombok.Data;

@Data
public class LoginEmailDTO {
    private String email;
    private String password;
    private String code;
}
