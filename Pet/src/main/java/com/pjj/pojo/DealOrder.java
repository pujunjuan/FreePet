package com.pjj.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class DealOrder {
    private Integer oiid;

    private Integer oid;

    private String dealWay;

    private String dealResulst;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dealtime;

    private String comment;


}