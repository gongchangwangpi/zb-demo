package com.spring.controller;

import com.spring.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by books on 2018/1/2.
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {
    
    private Logger logger = LoggerFactory.getLogger(StudentController.class);
    
    @GetMapping(value = "/{id}")
    public Student get(Long id) {
        return new Student(1L, "TestStudent", 18, "123456789");
    }
    
    @PostMapping(value = "/save")
    public Object save(@Valid Student student, BindingResult result) {

        logger.info("save student: {}", student);
        
        if (result.hasErrors()) {
            return getErrorTips(result);
        }
        
        return student;
    }
    
    @PostMapping(value = "/update")
    public Object update(@Valid Student student, BindingResult result) {

        logger.info("update student: {}", student);

        if (result.hasErrors()) {
            return getErrorTips(result);
        }

        return student;
    }

    private List<String> getErrorTips(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorTips = new ArrayList<>();
        for (FieldError fieldError: fieldErrors) {
            errorTips.add(fieldError.getDefaultMessage());
        }
        return errorTips;
    }
}
