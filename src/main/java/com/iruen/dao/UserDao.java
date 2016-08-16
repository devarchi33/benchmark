package com.iruen.dao;

import com.iruen.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Repository
public interface UserDao {

    void signUpUser(User user);

    String dummyQuery();

    List<User> findAllUsers();

    User findUserByEmail(String email);

    Boolean isValidUser(User user);

    void updateUser(User user);

    void deleteUserByEmail(String email);
}
