package com.springjpatransactional.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by pablo on 28/08/18.
 */
@Entity
@Table(name = "student")
public class StudentDTO implements Serializable{
    
    private Integer id;
    private Integer age;
    private String name;
    private CourseDTO courseDTO;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "name")
    public String getName() {
        return name;
    }
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = true)
    public CourseDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }
    
    @Transient
    public boolean isAdult() {
        return age > 20;
    }
}
