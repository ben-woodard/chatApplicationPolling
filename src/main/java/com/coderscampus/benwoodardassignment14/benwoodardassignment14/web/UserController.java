package com.coderscampus.benwoodardassignment14.benwoodardassignment14.web;

import com.coderscampus.benwoodardassignment14.benwoodardassignment14.domain.User;
import com.coderscampus.benwoodardassignment14.benwoodardassignment14.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity createUser(@RequestBody User user) {
        if (user.getName() == null || user.getName().equals("null") || user.getName().isEmpty()) {
            return new ResponseEntity("A User was submitted with an invalid username", HttpStatus.BAD_REQUEST);
        }
        userService.save(user);
        if (userService.findById(user.getUserId()).equals(user)) {
            return new ResponseEntity(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error in saving the user", HttpStatus.BAD_REQUEST);
        }
    }

}
