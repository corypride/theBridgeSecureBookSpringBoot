package org.launchcode.theBridge2.data;

import org.launchcode.theBridge2.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {

    User findByUsername(String username);

    User  findByLastNameAndFirstNameAndMiddleNameAndSuffix(String lastName,
                                                           String firstName, String middleName, String suffix);

    List<User> findByAccountTypeOrderByLastNameAsc(int accountType);

    List<User> findByStudentIsNullOrderByFirstNameAsc();

    List<User> findByStudentIsNotNullOrderByFirstNameAsc();

    List<User> findByIdNotOrderByUsernameAsc(Integer userId);




}
