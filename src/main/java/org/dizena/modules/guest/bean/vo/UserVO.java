package org.dizena.modules.guest.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable
{
    private String id;
    private String token;
    private String roles;
    private String auths;

    private String pic;
    private String nickname;
    private String description;
    private String sex;
    private Integer birth;

    private String province;
    private String city;
    private String district;

}
