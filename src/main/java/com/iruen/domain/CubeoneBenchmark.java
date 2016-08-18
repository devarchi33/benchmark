package com.iruen.domain;

import lombok.Data;

/**
 * Created by donghoon on 2016. 8. 18..
 */
@Data
public class CubeoneBenchmark {

    private long time;

    public CubeoneBenchmark(long time) {
        this.time = time;
    }
}
