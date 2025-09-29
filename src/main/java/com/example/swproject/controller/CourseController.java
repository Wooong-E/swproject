package com.example.swproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {
    @GetMapping("/courses/course00")
    public String showCourse00Page() {
        return "courses/course00";
    }

    @GetMapping("/courses/course01")
    public String showCourse01Page() {
        return "courses/course01";
    }
}
