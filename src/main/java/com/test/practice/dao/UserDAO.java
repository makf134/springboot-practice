package com.test.practice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.test.practice.entity.User;

public interface UserDAO extends CrudRepository<User,Integer> {
	public List<User> findByEmail(String email);
}
