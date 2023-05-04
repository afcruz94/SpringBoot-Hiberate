package com.andrecruz.course.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.andrecruz.course.entities.User;
import com.andrecruz.course.repositories.UserRepository;
import com.andrecruz.course.services.exceptions.DatabaseException;
import com.andrecruz.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = userRepository.findById(id);

		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User user) {
		return userRepository.save(user);
	}

	public void delete(Long id) {
		if (!Objects.isNull(id))
			try {
				userRepository.deleteById(id);
			} catch (EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException(id);
			} catch (RuntimeException e) {
				throw new DatabaseException(e.getMessage());
			}
		else
			throw new ResourceNotFoundException(id);
	}

	public User update(User user, Long id) {
		try {
			User entity = userRepository.getReferenceById(id);
			updateData(entity, user);

			return userRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, User user) {
		entity.setEmail(user.getEmail());
		entity.setName(user.getName());
		entity.setPhone(user.getPhone());
	}
}
