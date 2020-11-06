package com.pjj.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Alias("pet")
@Data
public class Pet {
    private Integer pid;

    private String pname;

    private String psex;

    private String page;

    private String ptitle;

    private String pcontent;

    private String ptype;

    private String pmanner;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ptime;

    private String ptatus;

    private Transfer transfer;

    private  Goods goods;

    private  User user;


}