package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.CourseLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLevelRepository extends CrudRepository<CourseLevel,
        Integer> {
}