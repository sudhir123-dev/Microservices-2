package com.microservice.userservice.service;

import com.microservice.userservice.VO.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service")
public interface DepartmentProxy {

    @GetMapping("/departments/{id}")
    public Department getDepartment(@PathVariable Long id);

}
