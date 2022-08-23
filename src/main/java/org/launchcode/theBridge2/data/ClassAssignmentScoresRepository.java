package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.ClassAssignmentScores;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassAssignmentScoresRepository extends CrudRepository<ClassAssignmentScores, Integer> {
}
