package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.UserDto;
import com.kodilla.ecommercee.exception.UserDoesntExist;
import com.kodilla.ecommercee.exception.UserIsBlockedException;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.exception.UserWithGivenUserNameExist;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.UserRepository;
import com.kodilla.ecommercee.service.TokenService;
import com.kodilla.ecommercee.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserDbService userDbService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "GetUsers")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userDbService.getUsers();
        return ResponseEntity.ok(userMapper.mapToUserDtoList(users));
    }

    @PutMapping(value = "BlockUser")
    public ResponseEntity<String> blockUser(@RequestParam Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setIsBlocked(true);
        userRepository.save(user);
        return ResponseEntity.ok("This user is now blocked");
    }

    @PostMapping(value = "CreateUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws UserWithGivenUserNameExist {
        User user = userMapper.mapToUser(userDto);
        userDbService.saveUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) throws UserDoesntExist, UserIsBlockedException {
        User user = userRepository.findByUsername(username);
        String generateUserKey = tokenService.generateToken(username, password);
        user.setUserKey(generateUserKey);
        userRepository.save(user);
        return ResponseEntity.ok(generateUserKey);
    }
}