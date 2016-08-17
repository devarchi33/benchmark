package com.iruen.dao;

import com.iruen.domain.CubeoneTest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Repository
public interface CubeoneTestDao {

    List<CubeoneTest> findAllUsers();
}
