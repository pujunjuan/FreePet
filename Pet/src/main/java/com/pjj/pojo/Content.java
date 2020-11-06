package com.pjj.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class Content {
    private Integer cid;

    private String ctitle;

    private String content;

    private String cimg;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ctime;

}