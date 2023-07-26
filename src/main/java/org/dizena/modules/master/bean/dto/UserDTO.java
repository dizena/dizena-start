package org.dizena.modules.master.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable
{
    private String id;
    @NotNull(message = "账户不为空")
    private String account;
    @NotNull(message = "昵称不为空")
    private String nickname;
    @NotNull(message = "密码不为空")
    private String passwd;
    @NotEmpty(message = "角色不为空")
    private String roles;
    @NotEmpty(message = "权限不为空")
    private String auths;

}
