package com.iruen.service;

import com.iruen.dao.CubeoneTestDao;
import com.iruen.domain.CubeoneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Service
public class CubeoneTestServiceImpl implements CubeoneTestService {

    @Autowired
    private CubeoneTestDao cubeoneTestDao;

    @Override
    public List<CubeoneTest> findAllUsers() {
        return cubeoneTestDao.findAllUsers();
    }
}
