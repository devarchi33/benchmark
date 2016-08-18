package com.iruen.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 18..
 */
@Data
public class CubeoneBenchmark {

    private long time;
    private List<CubeoneTestUser> users;

    public CubeoneBenchmark(long time, List<CubeoneTestUser> users) {
        this.time = time;
        this.users = users;
    }
}
