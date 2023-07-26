package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "t_master_user")
@NoArgsConstructor
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -4490642721851851016L;
    @Id
    private String id;
    @Indexed(unique = true)
    private String account;
    private String passwd;
    private String salt;
    private String platform;


    private String nickname;
    private String pic;
    private String description;
    private String sex;
    private Integer birth;

    private String province;
    private String city;
    private String district;
    private String mobile;
    private String email;

    private String roles;
    private String auths;
    private Integer locked;
    private Long createTime;
    private Long updateTime;


    public User(String account, String nickname, String roles, String auths) {

        this.account = account;
        this.nickname = nickname;
        this.roles = roles;
        this.auths = auths;

    }

    public User(String id, String account, String nickname, String roles, String auths, Integer locked, Long createTime) {
        super();
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.roles = roles;
        this.auths = auths;
        this.locked = locked;
        this.createTime = createTime;
    }

}
