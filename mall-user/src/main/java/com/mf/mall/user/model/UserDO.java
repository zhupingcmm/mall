package com.mf.mall.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {

    private Long id;

    private String account;

    private String password;

    private String name;

    private Integer role;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
