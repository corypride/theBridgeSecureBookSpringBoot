package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.ReportName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportNameRepository extends CrudRepository<ReportName, Integer> {


}
