package com.microservice.userservice.service;

import com.microservice.userservice.VO.Department;
import com.microservice.userservice.VO.ResponseTemplateVO;
import com.microservice.userservice.entity.User;
import com.microservice.userservice.repository.UserRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentProxy departmentProxy;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findById(userId).get();
       // Department department = new RestTemplate().getForObject("http://localhost:9001/departments/"+user.getDepartmentId(),Department.class);
        Department department = departmentProxy.getDepartment(user.getDepartmentId());
        vo.setUser(user);
        vo.setDepartment(department);
        return vo;
    }
}
