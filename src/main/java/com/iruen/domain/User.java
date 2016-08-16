package com.iruen.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * Created by donghoon on 2016. 8. 16..
 */
@Data
@Alias("user")
public class User {

    private Long id;
    private String email;
    private String password;

}
