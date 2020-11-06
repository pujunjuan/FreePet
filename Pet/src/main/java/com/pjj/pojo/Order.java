package com.pjj.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data

public class Order {
    private Integer oid;

    private Integer gdid;

    private String ocreater;

    private Integer uid;

    private String stutas;

    private String oaddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectiontime;

    private String reason;

    private User user;//申请者信息

    private  Goods goods;//宠物信息


}