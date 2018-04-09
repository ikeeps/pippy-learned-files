package com.ikeeps.pippy.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ikeeps.pippy.authservice.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
