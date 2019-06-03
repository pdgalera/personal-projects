package com.springjpatransactional;

import com.springjpatransactional.dto.CourseDTO;
import com.springjpatransactional.dto.StudentDTO;
import com.springjpatransactional.service.CourseService;
import com.springjpatransactional.service.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        StudentService studentService = (StudentService) context.getBean("studentService");

        CourseService courseService = (CourseService) context.getBean("courseService");
        //StudentDAO studentDAO = new StudentDAO();

        System.out.println("Creating Student" );
        
        System.out.println("----Listing Record with ID = 2 -----" );
        StudentDTO student = new StudentDTO();
        student.setName("testStudent");
        student.setAge(23);
        
        System.out.print("Saving...");
        studentService.save(student);

        System.out.println("Looping findAll");
        for (StudentDTO studentDTO : studentService.findAll()) {
            System.out.println(studentDTO.getId() + " " + studentDTO.getName());
        }
        
        System.out.println("Looping findFilteredStudents");
        for (StudentDTO studentDTO : studentService.findFilteredStudents()) {
            System.out.println(studentDTO.getId() + " " + studentDTO.getName());
        }

        System.out.println("Find");
        student = studentService.find(1015);
        
        System.out.println("Updating");
        student.setAge(24);
        studentService.save(student);

        System.out.println("Delete");
        student = studentService.find(1021);
        //studentService.delete(student);
        
        System.out.println(studentService.findByName("Zarat").getName());

        System.out.println("Creating Course");
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("courseTest");
        courseDTO.setDescription("some Description");
        courseDTO = courseService.save(courseDTO);

        student.setCourseDTO(courseDTO);
        studentService.save(student);
        
        
    }
}