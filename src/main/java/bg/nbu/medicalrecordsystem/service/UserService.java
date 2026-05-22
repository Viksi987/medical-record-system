package bg.nbu.medicalrecordsystem.service;

import bg.nbu.medicalrecordsystem.entity.User;

public interface UserService {

    User saveUser(User user);

    User findByUsername(String username);

}