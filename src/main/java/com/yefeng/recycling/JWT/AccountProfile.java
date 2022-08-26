package com.yefeng.recycling.JWT;

import lombok.Data;

import java.io.Serializable;

@Data


public class AccountProfile implements Serializable {


    /**
     * id
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;
}