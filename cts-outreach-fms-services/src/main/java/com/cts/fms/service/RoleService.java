
package com.cts.fms.service;

import java.util.List;

import com.cts.fms.domain.Role;

public interface RoleService {
	
	/**
	 * This method is to create Role 
	 * 
	 * @param role
	 */
	void create(Role role);
	
	/**
	 * This method is used to get all roles
	 * 
	 * @return List Object of Role
	 */
	List<Role> getAllRoles();
	
	/**
	 * This method is to find the roles by name
	 * 
	 * @param roleName
	 * @return Role Object
	 */
	Role findByName(String roleName);
}
