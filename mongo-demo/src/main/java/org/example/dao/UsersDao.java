package org.example.dao;


import org.example.model.User;

import java.util.List;

public interface UsersDao {
    void create(User user);
    User read(String id);
    void update(User user);
    void delete(String id);
    List<User> findAll();
}
