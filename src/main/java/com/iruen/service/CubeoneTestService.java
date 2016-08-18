package com.iruen.service;

import com.iruen.domain.CubeoneTestUser;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
public interface CubeoneTestService {

    List<CubeoneTestUser> findAllUsers();

    CubeoneTestUser findUserByName(String name);
}
