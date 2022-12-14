package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.exception.UserWithGivenUserNameExist;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDbService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User saveUser(User user) throws UserWithGivenUserNameExist {
        User user1 = userRepository.findByUsername(user.getUsername());

        if (user1 == null) {
            return userRepository.save(user);
        }
        throw new UserWithGivenUserNameExist();
    }
}
