package com.springjpatransactional.controller;

import com.springjpatransactional.dto.StudentDTO;
import com.springjpatransactional.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentSecondController {
    @Autowired
    StudentService studentService;  

    /*It displays a form to input data, here "command" is a reserved request attribute 
     *which is used to display object data into form 
     */
    @RequestMapping("/studentform")
    public ModelAndView showform(){
        return new ModelAndView("studentform","command",new StudentDTO());
    }
    /*It saves object into database. The @ModelAttribute puts request data 
     *  into model object. You need to mention RequestMethod.POST method  
     *  because default request is GET*/
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("student") StudentDTO student){
        studentService.save(student);
        return new ModelAndView("redirect:/viewstudent");//will redirect to viewemp request mapping  
    }
    /* It provides list students in model object */
    @RequestMapping("/viewstudent")
    public ModelAndView viewstudent(){
        List<StudentDTO> list=studentService.findAll();
        return new ModelAndView("viewstudent","list",list);
    }
    /* It displays object data into form for the given id.  
     * The @PathVariable puts URL data into variable.*/
    @RequestMapping(value="/editstudent/{id}")
    public ModelAndView edit(@PathVariable int id){
        StudentDTO student = studentService.find(id);
        return new ModelAndView("studenteditform","command",student);
    }
    
    /* It deletes record for the given id in URL and redirects to /viewstudent */
    @RequestMapping(value="/deletestudent/{id}",method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id){
        studentService.delete(studentService.find(id));
        return new ModelAndView("redirect:/viewstudent");
    }
}
