package com.cts.fms.service.mapper;

import javax.inject.Named;

import com.cts.fms.domain.User;
import com.cts.fms.service.dto.UserDTO;

@Named
public class UserDTOMapper implements DTOMapper<User, UserDTO> {

	@Override
	public UserDTO apply(User domain) {
		UserDTO resultUser = new UserDTO();
		resultUser.setEmail(domain.getEmail());
		resultUser.setName(domain.getFirstName());
		resultUser.setRole(domain.getRoles().iterator().next().getName());
		return resultUser;
	}

}
