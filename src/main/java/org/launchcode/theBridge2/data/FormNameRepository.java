package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.FormName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormNameRepository extends CrudRepository<FormName, Integer> {
}
