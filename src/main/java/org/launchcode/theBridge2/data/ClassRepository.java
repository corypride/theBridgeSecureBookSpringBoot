package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.Class;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<Class, Integer> {
}
