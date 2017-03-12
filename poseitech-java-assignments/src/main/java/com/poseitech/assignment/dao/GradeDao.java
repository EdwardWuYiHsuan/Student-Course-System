package com.poseitech.assignment.dao;

import java.util.List;

import com.poseitech.assignment.entity.Grade;

public interface GradeDao {

	public Grade findByLevel(Character level);
	
	public List<Grade> findAll();
	
	public Grade saveOrUpdate(Grade grade);
	
	public void delete(Character level);
	
}
