package com.iruen.service;

import com.iruen.dao.UserDao;
import com.iruen.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void signUpUser(User user) {
        userDao.signUpUser(user);
    }

    @Override
    public String dummyQuery() {
        return userDao.dummyQuery();
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUser;

        allUser = userDao.findAllUsers();

        return allUser;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userDao.findUserByEmail(email);
        return user;
    }

    @Override
    public Boolean isValidUser(User user) {
        User findUser = userDao.findUserByEmail(user.getEmail());

        if (findUser == null) {
            return false;
        }

        Boolean isValidUser;

        if (user.getEmail().equals(findUser.getEmail()) && user.getPassword().equals(findUser.getPassword())) {
            isValidUser = true;
            return isValidUser;
        } else {
            isValidUser = false;
            return isValidUser;
        }
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
        userDao.deleteUserByEmail(email);
    }

}
