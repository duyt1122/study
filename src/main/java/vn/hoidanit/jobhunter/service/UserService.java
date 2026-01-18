package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import util.error.IdInvalidException;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User create(User user) {
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
}
