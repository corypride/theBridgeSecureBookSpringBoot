package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);
}
