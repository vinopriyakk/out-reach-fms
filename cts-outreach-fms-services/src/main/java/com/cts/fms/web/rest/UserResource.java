package com.cts.fms.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fms.domain.EventUserInfo;
import com.cts.fms.domain.User;
import com.cts.fms.repository.UserRepository;
import com.cts.fms.service.RoleService;
import com.cts.fms.service.UserService;
import com.cts.fms.service.dto.AuthenticationDTO;
import com.cts.fms.service.dto.UserDTO;
import com.cts.fms.service.mapper.DTOMapper;
import com.cts.fms.web.rest.errors.ErrorConstants;
import com.cts.fms.web.rest.errors.FMSBusinessException;
import com.cts.fms.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	private static final String ENTITY_NAME = "userManagement";

	private final UserRepository userRepository;

	private final UserService userService;

	private MessageSource messageSource;

	private PasswordEncoder passwordEncoder;

	private RoleService roleService;

	private DTOMapper<User, UserDTO> userDTOMapper;

	public UserResource(UserRepository userRepository, UserService userService, MessageSource messageSource,
			PasswordEncoder passwordEncoder, DTOMapper<User, UserDTO> userDTOMapper, RoleService roleService) {

		this.userRepository = userRepository;
		this.userService = userService;
		this.messageSource = messageSource;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.userDTOMapper = userDTOMapper;
	}

	@PostMapping("/users/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO authenticationDTO) {

		log.debug("Fetching user account with information: {}", authenticationDTO.getUsername());
		Optional<User> userOptional = Optional.ofNullable(userService.findByEmail(authenticationDTO.getUsername()));
		User user = userOptional
				.orElseThrow(() -> new FMSBusinessException(ErrorConstants.USER_ACCOUNT_INVALID_CREDENTIALS_ERROR_CODE,
						messageSource.getMessage(ErrorConstants.USER_ACCOUNT_INVALID_CREDENTIALS_ERROR_MSG, null,
								Locale.getDefault())));
		if (passwordEncoder.matches(authenticationDTO.getPassword(), user.getPassphrase())) {
			java.util.Map<String, String> result = new ConcurrentHashMap<>();
			result.put("userId", user.getEmail());
			result.put("role", user.getRoles().iterator().next().getName());
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			throw new FMSBusinessException("The user name or password is incorrect. Try again.");
		}

	}

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) throws URISyntaxException {
		log.debug("REST request to save User : {}", userDto);
		User user = new User();;
		if(!userDto.getRole().equalsIgnoreCase("ADMIN")) {
			EventUserInfo optionalInfo = Optional.ofNullable(userService.getEventUserInfo(userDto.getEmail()))
					.orElseThrow(() -> new FMSBusinessException("UserNot Found in the System"));
			user.setFirstName(optionalInfo.getEmpName());
		}
		
		if(userDto.getName() != null) {
			user.setFirstName(userDto.getName());
		}
		user.setEmail(userDto.getEmail());
		user.getRoles().add(roleService.findByName(userDto.getRole()));

		if (user.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
					.body(null);
			// Lowercase the user login before comparing with database
		} else if (userRepository.findByEmail(user.getEmail().toLowerCase()) != null) {
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
					.body(null);
		} else {
			User result = userService.saveUser(user);
			return ResponseEntity.created(new URI("/api/users/" + result.getEmail()))
					.headers(HeaderUtil.createAlert("userManagement.created", result.getEmail()))
					.body(userDTOMapper.apply(result));
		}
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
		final List<User> users = userService.getUsers();
		return new ResponseEntity<>(userDTOMapper.convertToList(users), HttpStatus.OK);
	}

	@DeleteMapping("/users/{email}")
	public ResponseEntity<Void> deleteUser(@PathVariable String email) {
		log.debug("REST request to delete User: {}", email);
		userService.deleteUser(email);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", email)).build();
	}

}
