package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.StudentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentStatusRepository extends CrudRepository<StudentStatus,
        Integer> {
}
