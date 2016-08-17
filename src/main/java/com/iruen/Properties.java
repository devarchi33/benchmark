package com.iruen;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by donghoon on 2016. 8. 16..
 */
@ConfigurationProperties(locations = {"classpath:/application.yml"})
@Data
@Component
public class Properties {

    /**
     * application.yml 로 부터 설정 값을 읽어 오기 위한 클래스.
     */
    private String dbType;
    private Map<String, Map<String, String>> servers = new HashMap<>();
    private String env;
    private String logPrint;

}
