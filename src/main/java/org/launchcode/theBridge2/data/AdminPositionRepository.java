package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.AdminPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminPositionRepository extends CrudRepository<AdminPosition,
        Integer> {
}
