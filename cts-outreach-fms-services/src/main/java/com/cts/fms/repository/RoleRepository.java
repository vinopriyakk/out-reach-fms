
package com.cts.fms.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.fms.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
