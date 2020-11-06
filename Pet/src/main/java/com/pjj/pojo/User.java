package com.pjj.pojo;

import lombok.Data;
import lombok.NonNull;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Alias("user")
@Data
public class User {
    private Integer uid;
    private String umenbership;

    private String uname;

    private String usex;

    private String uage;

    private String uaddress;
    @Pattern(regexp = "^1[3456789]\\d{9}$",message = "请输入正确的电话号码")
    private String uphone;

    private String upassoword;

   }