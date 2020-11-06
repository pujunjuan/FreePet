package com.pjj.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("admin")
public class Admin {
    private Integer aID;
    private  String aName;
    private String aPassword;

}
