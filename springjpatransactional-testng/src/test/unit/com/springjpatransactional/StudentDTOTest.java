package com.springjpatransactional;

import com.springjpatransactional.dto.StudentDTO;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by pablo on 07/09/18.
 */
public class StudentDTOTest {

    @Test
    public void testAdd() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setAge(21);
        assertTrue(studentDTO.isAdult());
    }
}
