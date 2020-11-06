package com.pjj.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("Transfer")
public class Transfer {
    private Integer tid;

    private Integer uid;

    private Integer gdid;

    private Integer pid;

    //获取宠物信息

    private Pet pet;




}