package com.springjpatransactional.controller;

import com.springjpatransactional.dto.StudentDTO;
import com.springjpatransactional.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.log4j.Logger;

/**
 * Created by pablo on 04/09/18.
 */
@Controller
public class StudentController {
    
    private static final Logger LOGGER = Logger.getLogger(StudentController.class);
    private StudentService studentService;

    @Autowired(required=true)
    public void setStudentService(StudentService ss){
        this.studentService = ss;
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String listStudents(Model model) {
        LOGGER.info("Listing students");
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("studentList", this.studentService.findAll());
        return "student";
    }

    //For add and update students both
    @RequestMapping(value= "/student/add", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("student") StudentDTO student){
        LOGGER.info("Saving student id " + student.getId());
        this.studentService.save(student);
        return "redirect:/students";

    }

    @RequestMapping("/remove/{id}")
    public String removeStudent(@PathVariable("id") int id){
        LOGGER.info("Removing student");
        this.studentService.delete(studentService.find(id));        
        return "redirect:/students";
    }

    @RequestMapping("/edit/{id}")
    public String editStudent(@PathVariable("id") int id, Model model){
        LOGGER.info("Editing student");
        model.addAttribute("student", this.studentService.find(id));
        model.addAttribute("listPersons", this.studentService.findAll());
        return "student";
    }
}
