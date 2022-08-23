package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.School;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends CrudRepository<School, Integer> {
}
