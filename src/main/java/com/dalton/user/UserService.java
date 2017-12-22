package com.dalton.user;

import java.util.List;

/**
 * Created by dalton on 22/12/17.
 */

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
}
