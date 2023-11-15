package io.javabrains.springbootquickstart.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.springbootquickstart.topic.Topic;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Gets all courses
    @RequestMapping("/topics/{topicId}/courses")
    public List<Course> getAllcourses(@PathVariable("topicId") String topicId) {
        return courseService.getAllCourses(topicId);
    }

    // Get the course
    @RequestMapping("/topics/{topicId}/courses/{courseId}")
    public Course getCourse(@PathVariable("courseId") String courseId) {
        return courseService.getCourse(courseId);
    }

    // Create new course
    @RequestMapping(method = RequestMethod.POST, value = "/topics/{topicId}/courses")
    public void addCourse(@RequestBody Course course, @PathVariable("topicId") String topicId) {
        course.setTopic(new Topic(topicId, "", ""));
        courseService.addCourse(course);
    }

    // Updates the course
    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{topicId}/courses/{courseId}")
    public void updateCourse(@RequestBody Course course, @PathVariable("topicId") String topicId) {
        course.setTopic(new Topic(topicId, "", ""));
        courseService.updateCourse(course);
    }

    // Deletes the course
    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{topicId}/courses/{courseId}")
    public void deleteCourse(@PathVariable("courseId") String courseId) {
        courseService.deleteCourse(courseId);
    }
}
