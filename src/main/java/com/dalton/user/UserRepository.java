package com.dalton.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dalton on 22/12/17.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
}

