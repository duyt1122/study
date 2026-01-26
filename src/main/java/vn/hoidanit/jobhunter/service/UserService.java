package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.error.IdInvalidException;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}

	public User findById(long id) {
		Optional<User> userOptional = this.userRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		}
		return null;
	}

	public void delete(long id) throws IdInvalidException {
		User user = this.findById(id);
		if (user == null) {
			throw new IdInvalidException("user không tồn tại với id = " + id);
		}
		this.userRepository.deleteById(id);
	}

	public List<User> findAllUsers() {
		return this.userRepository.findAll();
	}

	public User update(User user) throws IdInvalidException {
		User currentUser = this.findById(user.getId());
		if (currentUser == null) {
			throw new IdInvalidException("user không tồn tại với id = " + user.getId());
		}
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPassword(passwordEncoder.encode(user.getPassword()));

		return this.userRepository.save(currentUser);

	}

	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
}
