package com.cts.fms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Named;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.fms.domain.EventUserInfo;
import com.cts.fms.domain.User;
import com.cts.fms.repository.EventUserInfoRepository;
import com.cts.fms.repository.UserRepository;
import com.cts.fms.web.rest.errors.FMSBusinessException;
import com.cts.fms.web.rest.errors.UserNotFoundException;

@Named
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private EventUserInfoRepository eventUserInfoRepository;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			EventUserInfoRepository eventUserInfoRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.eventUserInfoRepository = eventUserInfoRepository;
	}

	@Override
	public List<User> getUsers() {
		List<User> userList = new ArrayList<>();
		userRepository.findAll().forEach(u -> userList.add(u));
		return userList;
	}

	@Override
	public User getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Override
	public User saveUser(User user) {
		user.setPassphrase(passwordEncoder.encode("Test@123"));
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String email) {
		User user = userRepository.findByEmail(email);
		userRepository.delete(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveAllUser(List<User> users) {
		userRepository.saveAll(users);

	}
	@Override
	public EventUserInfo getEventUserInfo(String email) {
		return Optional.ofNullable(eventUserInfoRepository.findByEmployeeId(email))
				.orElseThrow(() -> new FMSBusinessException("UserNot Found in the System"));
	}
}
