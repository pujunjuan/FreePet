package com.pjj.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("petype")
public class Pettype {
    private Integer gid;
    private String gtypename;


}