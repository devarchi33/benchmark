package com.iruen.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Data
@Alias("test")
public class CubeoneTestUser {

    private String name;
    private String jumin;
    private String jumin_origin;

}
