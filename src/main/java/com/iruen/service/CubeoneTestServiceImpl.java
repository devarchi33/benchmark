package com.iruen.service;

import com.iruen.dao.CubeoneTestDao;
import com.iruen.domain.CubeoneTestUser;
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
    public List<CubeoneTestUser> findAllUsers() {
        return cubeoneTestDao.findAllUsers();
    }

    @Override
    public List<CubeoneTestUser> findKey() {
        return cubeoneTestDao.findKey();
    }

    @Override
    public CubeoneTestUser findUserByName(String name) {
        return cubeoneTestDao.findUserByName(name);
    }
}
