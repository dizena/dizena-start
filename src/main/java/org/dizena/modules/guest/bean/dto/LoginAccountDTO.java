package org.dizena.modules.guest.bean.dto;

import lombok.Data;

@Data
public class LoginAccountDTO {
    private String account;
    private String password;
    private String uuid;
    private String code;
}
