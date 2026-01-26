package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.error.IdInvalidException;
import vn.hoidanit.jobhunter.service.UserService;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<User> create(@RequestBody User user) {
		User createUser = this.userService.create(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {
		this.userService.delete(id);
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> findAll() {
		List<User> users = this.userService.findAllUsers();
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") long id) throws IdInvalidException {
		User user = this.userService.findById(id);
		if (user == null) {
			throw new IdInvalidException("user không tồn tại với id = " + id);
		}
		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/users")
	public ResponseEntity<User> update(@RequestBody User user) throws IdInvalidException {
		return ResponseEntity.ok().body(this.userService.update(user));
	}
}
