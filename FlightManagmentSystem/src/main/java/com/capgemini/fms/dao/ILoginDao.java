package com.capgemini.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.fms.entity.User;


@Repository
public interface ILoginDao  extends JpaRepository<User, String>{


}