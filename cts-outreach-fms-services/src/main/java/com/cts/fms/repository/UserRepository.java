package com.cts.fms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cts.fms.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	@Query("Select user from User user where user.email = :email")
	User findByEmail(@Param("email") String email);
}
