package com.microservice.userservice.controller;

import com.microservice.userservice.VO.ResponseTemplateVO;
import com.microservice.userservice.entity.User;
import com.microservice.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    Logger logger =  LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    @Retry(name = "default", fallbackMethod = "hardCodedResponse")
    //@CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    public ResponseEntity<ResponseTemplateVO> getUserWithDepartment(@PathVariable("id") Long userId){
        logger.info("department-service api call received");
        ResponseTemplateVO responseTemplateVO
                = userService.getUserWithDepartment(userId);
        return new ResponseEntity<ResponseTemplateVO>(responseTemplateVO,HttpStatus.OK);
    }

    public ResponseEntity<Object> hardCodedResponse(Exception e){
        return new ResponseEntity<Object>("Department service is down, please try after sometime.",HttpStatus.FORBIDDEN);
    }
}
