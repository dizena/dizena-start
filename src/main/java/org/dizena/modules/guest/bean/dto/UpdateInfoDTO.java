package org.dizena.modules.guest.bean.dto;

import lombok.Data;

@Data
public class UpdateInfoDTO {
    private String nickname;
    private String pic;
    private String description;
    private String sex;
    private Integer birth;

    private String province;
    private String city;
    private String district;
    
}
