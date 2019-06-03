package com.springjpatransactional.service;

import com.springjpatransactional.db.AbstractDAO;
import com.springjpatransactional.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by pablo on 30/08/18.
 */
@Service
@Transactional
public class CourseService {

    private AbstractDAO<CourseDTO> courseDAO;

    @Autowired
    public CourseService(AbstractDAO<CourseDTO> courseDAO) {
        this.courseDAO = courseDAO;
        this.courseDAO.setPersistentClass(CourseDTO.class);
    }

    public CourseDTO save(CourseDTO course) {
        return courseDAO.save(course);
    }

    public List<CourseDTO> findAll() {
        return courseDAO.findAll();
    }

    public CourseDTO find(Serializable id) {
        return courseDAO.find(id);
    }

    public void delete(CourseDTO courseDTO) {
        courseDAO.delete(courseDTO);
    }
}
