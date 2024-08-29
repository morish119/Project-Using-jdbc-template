package com.simpleProject.service;

import com.simpleProject.entity.UserDetails;

import com.simpleProject.exception.UserNotFoundException;
import com.simpleProject.repository.repoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsService {
    @Autowired
    private repoUser dao;

    public int saveUser(UserDetails entity) {
        return dao.saveUser(entity);
    }
    public Object getUserIDById(String id) {
            Object userIDById = dao.getUserIDById(id);
            return userIDById;

        }


    public List<UserDetails> getAllUserDetails() {
        return dao.getAllUserDetails();
    }

    public int deleteUser(String id) {
        return dao.deleteUser(id);
    }



    public int updateEntity(String id,UserDetails userDetails) {
        return dao.updateEntity(id,userDetails);
    }
}
