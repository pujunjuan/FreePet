package com.pjj.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class News {
    private Integer nid;

    private String ntitle;

    private String ncontent;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ntime;



}