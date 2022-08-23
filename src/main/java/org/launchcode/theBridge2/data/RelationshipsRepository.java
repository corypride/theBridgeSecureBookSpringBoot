package org.launchcode.theBridge2.data;


import org.launchcode.theBridge2.models.Relationships;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipsRepository extends CrudRepository<Relationships,
 Integer> {
}

