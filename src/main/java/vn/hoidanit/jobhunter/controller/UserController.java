package vn.hoidanit.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import util.error.IdInvalidException;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("users")
	public ResponseEntity<User> create(@RequestBody User user) {
		User createUser = this.userService.create(user);
		return ResponseEntity.ok().body(createUser);
	}

	@DeleteMapping("users/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {
		this.userService.delete(id);
		return ResponseEntity.ok().body(null);
	}
}
