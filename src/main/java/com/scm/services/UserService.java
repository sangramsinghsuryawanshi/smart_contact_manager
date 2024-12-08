package com.scm.services;

import com.scm.model.Users;
import java.util.*;
public interface UserService 
{
    Users saveUser(Users user);
    Optional<Users> getUserById(String id);
    Optional<Users> updateUser(Users user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String userEmail);
    List<Users> getAllUsers();
}
