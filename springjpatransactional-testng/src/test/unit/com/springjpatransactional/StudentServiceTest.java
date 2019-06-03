package com.springjpatransactional;

import com.springjpatransactional.dto.StudentDTO;
import com.springjpatransactional.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by pablo on 10/09/18.
 */

@Test
@ContextConfiguration(locations = { "classpath:spring-beans.xml" })
public class StudentServiceTest extends AbstractTestNGSpringContextTests {
    
    private static final Logger LOGGER = Logger.getLogger(StudentServiceTest.class);
    private static String STUDENT_TEST_NAME = "Name_"; 
    
    @Autowired
    private StudentService studentService;
  
    @BeforeClass
    public void setUp() {
        LOGGER.info("Starting test");
    }
    
    @Test
    public void createStudentTest() {
        
        StudentDTO studentDTO = studentService.save(createStudent());
        Assert.assertNotNull(studentDTO.getId(), "id should not be null");
    }

    @Test
    public void findStudentTest() {
        StudentDTO studentDTO = studentService.save(createStudent());
        Assert.assertNotNull(studentService.find(studentDTO.getId()), "find by id must work");
    }
    
    @Test
    public void updateStudentTest() {
        StudentDTO studentDTO = studentService.save(createStudent());
        studentDTO.setAge(20);
        studentService.save(studentDTO);
        Assert.assertTrue(studentService.find(studentDTO.getId()).getAge().equals(20), "Updated age should be 20");
    }

    @Test
    public void findByNameStudentTest() {
        StudentDTO studentDTO = studentService.save(createStudent());
        Assert.assertNotNull(studentService.findByName(studentDTO.getName()), "find by name must work");
    }
    
    @Test
    public void deleteStudentTest() {
        StudentDTO studentDTO = studentService.save(createStudent());
        studentService.delete(studentDTO);
        Assert.assertNull(studentService.find(studentDTO.getId()), "Removed student should be null");
    }
    
    private StudentDTO createStudent() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(STUDENT_TEST_NAME + System.currentTimeMillis());
        studentDTO.setAge(19);
        return studentDTO;
    }
}
