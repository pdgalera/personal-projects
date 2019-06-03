package com.springjpatransactional.service;

import com.springjpatransactional.db.AbstractDAO;
import com.springjpatransactional.dto.StudentDTO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by pablo on 29/08/18.
 */
@Service
@Transactional
public class StudentService {
        
    private AbstractDAO<StudentDTO> studentDAO;

    @Autowired
    public StudentService (AbstractDAO<StudentDTO> studentDAO) {
        this.studentDAO = studentDAO;
        this.studentDAO.setPersistentClass(StudentDTO.class);
    }
    
    public StudentDTO save(StudentDTO student) {
        return studentDAO.save(student);
    }

    public List<StudentDTO> findAll() {
        return studentDAO.findAll();
    }

    public StudentDTO find(Serializable id) {
        return studentDAO.find(id);
    }

    public void delete(StudentDTO studentDTO) {
        studentDAO.delete(studentDTO);
    }

    public List<StudentDTO> findFilteredStudents() {
        Criteria crit = studentDAO.getSession().createCriteria(StudentDTO.class)
                .add(Restrictions.ge("age", 11));
        return crit.list();
    }

    private static final String FIND_STUDENT_BY_NAME_QUERY =
            "select student "
                    + " from StudentDTO student "
                    + " where student.name = :name ";

    public StudentDTO findByName(String name) {
        Query query = studentDAO.getSession().createQuery(FIND_STUDENT_BY_NAME_QUERY);
        query.setParameter("name", name);
        return (StudentDTO) query.uniqueResult();
    }
}
