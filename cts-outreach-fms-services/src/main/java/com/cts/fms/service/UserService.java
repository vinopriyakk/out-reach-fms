package com.cts.fms.service;

import java.util.List;

import com.cts.fms.domain.EventUserInfo;
import com.cts.fms.domain.User;

public interface UserService {

    List<User> getUsers();

    User getUser(Long id);

    User saveUser(User user);

    void deleteUser(String eamil);
    
    User findByEmail(String email);
    
    void saveAllUser(List<User> users);
    
    public EventUserInfo getEventUserInfo(String email);
}
