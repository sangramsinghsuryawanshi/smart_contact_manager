package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.helpers.AppComponent;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.model.Users;
import com.scm.repositries.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepo userRepo,PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users saveUser(Users user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppComponent.ROLE_USER));
        return userRepo.save(user);
    }

    @Override
    public Optional<Users> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<Users> updateUser(Users updatedUserDetails) {
        Users existingUser = userRepo.findById(updatedUserDetails.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + updatedUserDetails.getUserId()));

        existingUser.setUserName(updatedUserDetails.getUsername());
        existingUser.setUserEmail(updatedUserDetails.getUserEmail());
        existingUser.setPassword(updatedUserDetails.getPassword());
        existingUser.setAbout(updatedUserDetails.getAbout());
        existingUser.setProfilePic(updatedUserDetails.getProfilePic());
        existingUser.setPhoneNumber(updatedUserDetails.getPhoneNumber());
        existingUser.setEnabled(updatedUserDetails.isEnabled());
        existingUser.setEmailVerified(updatedUserDetails.isEmailVerified());
        existingUser.setPhoneVerified(updatedUserDetails.isPhoneVerified());
        existingUser.setProvider(updatedUserDetails.getProvider());
        existingUser.setProviderUserId(updatedUserDetails.getProviderUserId());

        Users users = userRepo.save(existingUser);
        return Optional.ofNullable(users);
    }

    @Override
    public void deleteUser(String id) {
        Users delUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        userRepo.delete(delUser);
    }

    @Override
    public boolean isUserExist(String userId) {
        Users userExits = userRepo.findById(userId).orElse(null);
        return userExits != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String userEmail) {
        Users user = userRepo.findByUserEmail(userEmail).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

}
