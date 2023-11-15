package io.javabrains.springbootquickstart.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository <Course, String> {
    // findByProperty, Spring Data JPA will implement the method
    public List<Course> findByName(String name);

    // findByPropertyKey
    public List<Course> findByTopicId(String topicId);
}
