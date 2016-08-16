package com.iruen.service;

import com.iruen.dao.UserDao;
import com.iruen.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Service
public interface UserService {

    @Autowired
    UserDao userDao = null;

    void signUpUser(User user);

    String dummyQuery();

    List<User> findAllUsers();

    User findUserByEmail(String email);

    Boolean isValidUser(User user);

    void updateUser(User user);

    void deleteUserByEmail(String email);


}
