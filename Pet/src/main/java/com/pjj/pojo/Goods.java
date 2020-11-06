package com.pjj.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@Alias("Goods")
public class Goods {
    private Integer gdid;

    private String gdsex;

    private Integer gdtype;

    private String gdname;

    private String gdcontext;

    private String gdtitle;

    private String gdage;

    private String gdstate;

    private String gdimg;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gdtime;

    private Pettype pettype;

}