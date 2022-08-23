package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.ClassAssignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassAssignmentRepository extends CrudRepository<ClassAssignment,
        Integer> {
}
