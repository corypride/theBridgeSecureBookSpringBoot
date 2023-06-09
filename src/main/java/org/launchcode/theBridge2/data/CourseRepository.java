package org.launchcode.theBridge2
        .data;

import org.launchcode.theBridge2
        .models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
}
