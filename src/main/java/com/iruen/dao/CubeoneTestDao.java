package com.iruen.dao;

import com.iruen.domain.CubeoneTestUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Repository
public interface CubeoneTestDao {

    List<CubeoneTestUser> findAllUsers();

    CubeoneTestUser findUserByName(String name);
}
