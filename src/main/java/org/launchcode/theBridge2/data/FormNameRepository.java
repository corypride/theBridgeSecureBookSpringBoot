package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.FormName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormNameRepository extends CrudRepository<FormName, Integer> {

    List<FormName> findAll();
}
