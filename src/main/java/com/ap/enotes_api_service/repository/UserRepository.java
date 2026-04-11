package com.ap.enotes_api_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ap.enotes_api_service.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
