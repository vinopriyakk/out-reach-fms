
package com.cts.fms.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.cts.fms.domain.Role;
import com.cts.fms.repository.RoleRepository;

@Named
@Transactional
public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;
	
	@Inject
	public RoleServiceImpl(RoleRepository roleRepository){
		this.roleRepository = roleRepository;
		
	}
    /**
     * Specified by: {@code create} in interface {@code RoleService}
     */
	@Override
	public void create(Role role) {
		roleRepository.save(role);

	}
    /**
     * Specified by: {@code getAllRoles} in interface {@code RoleService}
     */
	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
    /**
     * Specified by: {@code findByName} in interface {@code RoleService}
     */
	@Override
	public Role findByName(String roleName) {
		return roleRepository.findByName(roleName);
	}

}
