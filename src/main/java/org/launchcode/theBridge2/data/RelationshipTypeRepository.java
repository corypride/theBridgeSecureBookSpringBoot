package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.RelationshipType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipTypeRepository extends CrudRepository<RelationshipType, Integer> {
}
