package io.javabrains.springbootquickstart.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicControllerStatic {
    @Autowired
    private TopicServiceStatic topicServiceStatic;

    // Gets all topics
    @RequestMapping("/topics-static")
    public List<Topic> getAllTopics() {
        return topicServiceStatic.getAllTopics();
    }

    // Get the topic
    @RequestMapping("/topics-static/{id}")
    public Topic getTopic(@PathVariable("id") String id) {
        return topicServiceStatic.getTopic(id);
    }

    // Create new topic
    @RequestMapping(method = RequestMethod.POST, value = "/topics-static")
    public void addTopic(@RequestBody Topic topic) {
        topicServiceStatic.addTopic(topic);
    }

    // Updates the topic
    @RequestMapping(method = RequestMethod.PUT, value = "/topics-static/{id}")
    public void addTopic(@RequestBody Topic topic, @PathVariable("id") String id) {
        topicServiceStatic.updateTopic(id, topic);
    }

    // Deletes the topic
    @RequestMapping(method = RequestMethod.DELETE, value = "/topics-static/{id}")
    public void deleteTopic(@PathVariable("id") String id) {
        topicServiceStatic.deleteTopic(id);
    }
}
