package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.CourseGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseGenreRepository extends CrudRepository <CourseGenre,
        Integer> {
}
